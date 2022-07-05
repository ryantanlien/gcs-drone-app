/*
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


import java.util.Map;

import com.luciad.view.lightspeed.style.imagefilter.TLspColorLookupTableFilterStyle;

/**
 * A {@link TLspColorLookupTableFilterStyle} with
 * some additional metadata to allow updating it from a GUI.
 */
public class RasterFilter {

  private final String fName;
  private final Map<Object, Object> fProperties;
  private final TLspColorLookupTableFilterStyle fStyle;

  /**
   * Creates a new image filter.
   *
   * @param aName       the name of the filter
   * @param aProperties the properties of the filter
   * @param aStyle      the style
   */
  public RasterFilter( String aName, Map<Object, Object> aProperties, TLspColorLookupTableFilterStyle aStyle ) {
    fName = aName;
    fProperties = aProperties;
    fStyle = aStyle;
  }

  /**
   * Returns the name of the filter.
   *
   * @return the name
   */
  public String getName() {
    return fName;
  }

  /**
   * Returns a property of the filter.
   *
   * @param aKey the property key
   *
   * @return the property or {@code null}
   */
  public Object getProperty( String aKey ) {
    return fProperties == null ? null : fProperties.get( (Object)aKey );
  }

  /**
   * Returns the style of the filter.
   *
   * @return the style
   */
  public TLspColorLookupTableFilterStyle getStyle() {
    return fStyle;
  }

  @Override
  public String toString() {
    return fName;
  }

  @Override
  public boolean equals( Object o ) {
    if ( this == o ) {
      return true;
    }
    if ( !( o instanceof RasterFilter ) ) {
      return false;
    }

    RasterFilter that = ( RasterFilter ) o;

    if ( !fName.equals( that.fName ) ) {
      return false;
    }
    if ( !fProperties.equals( that.fProperties ) ) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = fName.hashCode();
    result = 31 * result + fProperties.hashCode();
    return result;
  }
}
