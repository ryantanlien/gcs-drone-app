package standalone.sample;/*
 *
 * Copyright (c) 1999-2013 Luciad All Rights Reserved.
 *
 * Luciad grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Luciad.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. LUCIAD AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL LUCIAD OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF LUCIAD HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 */


import java.awt.Color;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.luciad.util.ELcdInterpolationType;
import com.luciad.view.lightspeed.TLspContext;
import com.luciad.view.lightspeed.style.ALspStyle;
import com.luciad.view.lightspeed.style.ILspWorldElevationStyle.ElevationMode;
import com.luciad.view.lightspeed.style.TLspFillStyle;
import com.luciad.view.lightspeed.style.TLspLineStyle;
import com.luciad.view.lightspeed.style.TLspRasterStyle;
import com.luciad.view.lightspeed.style.styler.ALspStyleCollector;
import com.luciad.view.lightspeed.style.styler.ALspStyler;

/**
 * Styler that submits {@link TLspRasterStyle} objects.
 * <p/>
 * The styler is configurable and fires an event when it is modified.
 */
public class RasterStyler extends ALspStyler {

  private TLspRasterStyle fStyle;
  private RasterFilter fFilter;
  private TLspLineStyle fBoundsOutlineStyle;
  private TLspFillStyle fBoundsFillStyle;
  private List<ALspStyle> fStyleList;
  private static final float opacity = 1.f;
  private static final float opacityDivision = 255f;

  public RasterStyler() {
    this( opacity );
  }

  public RasterStyler( float aOpacity ) {
    this( TLspRasterStyle.newBuilder().opacity( aOpacity ).build() );
  }

  public RasterStyler( TLspRasterStyle aRasterStyle ) {
    fStyle = aRasterStyle;
    invalidateStyles();
    fBoundsOutlineStyle = TLspLineStyle.newBuilder().color(Color.WHITE).elevationMode( ElevationMode.ON_TERRAIN ).build();
    fBoundsFillStyle = TLspFillStyle.newBuilder().color(Color.RED).stipplePattern(TLspFillStyle.StipplePattern.HATCHED).elevationMode( ElevationMode.ON_TERRAIN ).build();
  }

  public void setBrightness( float aBrightness ) {
    fStyle = fStyle.asBuilder().brightness( aBrightness ).build();
    invalidateStyles();
  }

  public void setContrast( float aContrast ) {
    fStyle = fStyle.asBuilder().contrast( aContrast ).build();
    invalidateStyles();
  }

  public void setOpacity( float aOpacity ) {
    fStyle = fStyle.asBuilder().opacity( aOpacity ).build();
    invalidateStyles();
  }

  public void setInterpolationType( ELcdInterpolationType aInterpolationType ) {
    fStyle = fStyle.asBuilder().interpolation( aInterpolationType ).build();
    invalidateStyles();
  }

  public float getOpacity() {
    return ((float) fStyle.getModulationColor().getAlpha() / opacityDivision);
  }

  public float getBrightness() {
    return fStyle.getBrightness();
  }

  public float getContrast() {
    return fStyle.getContrast();
  }

  public ELcdInterpolationType getInterpolationType() {
    return fStyle.getInterpolationType();
  }

  public void setColorModel( ColorModel aColorModel ) {
    if(aColorModel.equals(getColorModel())) {
      fStyle = fStyle.asBuilder().colorModel( aColorModel ).build();
      invalidateStyles();
    }
  }

  public ColorModel getColorModel() {
    return fStyle.getColorModel();
  }

  public void setColor( Color aColor ) {
    // Keep alpha value
    float color[] = new float[ 4 ];
    fStyle.getModulationColor().getComponents( color );
    float alpha = color[ 3 ];
    aColor.getComponents( color );
    color[ 3 ] = alpha;
    fStyle = TLspRasterStyle.newBuilder().all(fStyle).modulationColor( new Color( color[0], color[1], color[2], color[3] ) ).build();
    invalidateStyles();
  }

  public Color getBoundsColor() {
    return fBoundsOutlineStyle.getColor();
  }

  public void setBoundsColor(Color aColor) {
    if(!getBoundsColor().equals(aColor)) {
      fBoundsOutlineStyle = fBoundsOutlineStyle.asBuilder().color(aColor).build();
      fBoundsFillStyle = fBoundsFillStyle.asBuilder().color(aColor).build();
      invalidateStyles();
    }
  }

  public RasterFilter getFilter() {
    return fFilter;
  }

  public void setFilter( RasterFilter aFilter ) {
    if ( fFilter == null  ? aFilter != null : !fFilter.equals( aFilter ) ) {
      fFilter = aFilter;
      invalidateStyles();
    }
  }

  @Override
  public void style( Collection<?> aObjects, ALspStyleCollector aStyleCollector, TLspContext aContext) {
    aStyleCollector.objects(aObjects).styles(getStyles()).submit();
  }

  private List<ALspStyle> getStyles() {
    if ( fStyleList == null ) {
      ArrayList<ALspStyle> styles = new ArrayList<ALspStyle>( 4 );
      styles.add( fStyle );
      if ( fFilter != null && fFilter.getStyle() != null ) {
        styles.add( fFilter.getStyle() );
      }
      styles.add( fBoundsOutlineStyle );
//      styles.add( fBoundsFillStyle );
      fStyleList = styles;
    }
    return fStyleList;
  }

  private void invalidateStyles() {
    fStyleList = null;
    fireStyleChangeEvent();
  }
}
