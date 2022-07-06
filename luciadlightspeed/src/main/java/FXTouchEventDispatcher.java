import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

import com.luciad.input.TLcdAWTEventDispatcher;
import com.luciad.input.touch.TLcdTouchEvent;
import com.luciad.input.touch.TLcdTouchPoint;

import javafx.embed.swing.SwingNode;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TouchPoint;

/**
 * Translates and dispatches FX touch events into TLcdTouchEvents. This requires an FX application with a SwingNode.
 */
public class FXTouchEventDispatcher implements EventDispatcher {
	private final SwingNode fSwingNode;
	private final EventDispatcher fRegularEventDispatcher;

	private final Map<Integer, Component> fTouchedComponents = new HashMap<>();
	
	private double touchX1 = 0;
	private double touchY1 = 0;
	private double touchX2 = 0;
	private double touchY2 = 0;
	private final static double THRESHOLD = 700;
	
	public static void install(SwingNode aSwingNode) {
		EventDispatcher eventDispatcher = aSwingNode.getEventDispatcher();
		aSwingNode.setEventDispatcher(new FXTouchEventDispatcher(aSwingNode, eventDispatcher));

	}

	private FXTouchEventDispatcher(SwingNode aSwingNode, EventDispatcher aRegularEventDispatcher) {
		fSwingNode = aSwingNode;
		fRegularEventDispatcher = aRegularEventDispatcher;
	}
	@Override
	public Event dispatchEvent(Event event, EventDispatchChain tail) {
		if (event instanceof TouchEvent) {
			EventQueue.invokeLater(() -> {
				TLcdTouchEvent e = convertTouchEvent(event, fSwingNode);
				if (e != null) {
					try{
						TouchEvent touchEvent = (TouchEvent) event;
						if (touchEvent.getTouchPoint().getState() == TouchPoint.State.PRESSED) {
							touchX1 = e.getTouchPoints().get(0).getLocation().getX();
							touchY1 = e.getTouchPoints().get(0).getLocation().getY();
							if(e.getTouchPoints().size() > 1){
								touchX2 = e.getTouchPoints().get(1).getLocation().getX();
								touchY2 = e.getTouchPoints().get(1).getLocation().getY();
							}
						}
						if (touchEvent.getTouchPoint().getState() == TouchPoint.State.MOVED) {
							
							double tempTouchX1 = e.getTouchPoints().get(0).getLocation().getX();
							double tempTouchY1 = e.getTouchPoints().get(0).getLocation().getY();
							if(Math.abs((tempTouchX1 - touchX1)) > THRESHOLD || Math.abs((tempTouchY1 - touchY1)) > THRESHOLD){
								return;
							}
							if(e.getTouchPoints().size() > 1){
								double tempTouchX2 = e.getTouchPoints().get(1).getLocation().getX();
								double tempTouchY2 = e.getTouchPoints().get(1).getLocation().getY();
								if(Math.abs((tempTouchX2 - touchX2)) > THRESHOLD || Math.abs((tempTouchY2 - touchY2)) > THRESHOLD){
									return;
								}else{
									touchX2 = tempTouchX2;
									touchY2 = tempTouchY2;
								}
							}
							touchX1 = tempTouchX1;
							touchY1 = tempTouchY1;
						}
						if (touchEvent.getTouchPoint().getState() == TouchPoint.State.RELEASED) {
							double tempTouchX1 = e.getTouchPoints().get(0).getLocation().getX();
							double tempTouchY1 = e.getTouchPoints().get(0).getLocation().getY();
							if(Math.abs((tempTouchX1 - touchX1)) > THRESHOLD || Math.abs((tempTouchY1 - touchY1)) > THRESHOLD){
								 e.getTouchPoints().get(1).getLocation().setLocation(touchX1, touchY1);

							}
							if(e.getTouchPoints().size() > 1){
								double tempTouchX2 = e.getTouchPoints().get(1).getLocation().getX();
								double tempTouchY2 = e.getTouchPoints().get(1).getLocation().getY();
								if(Math.abs((tempTouchX2 - touchX2)) > THRESHOLD || Math.abs((tempTouchY2 - touchY2)) > THRESHOLD){
									 e.getTouchPoints().get(1).getLocation().setLocation(touchX2, touchY2);
								}
							}
							
						}
						TLcdAWTEventDispatcher.getInstance().handleAWTEvent(e);
						
					}catch(Exception ee){
//						try{
//
//						}catch(Exception eee){
//						}
					}
				}
			});
		}
		// also dispatch as mouse events for the swing hierarchy
		return fRegularEventDispatcher.dispatchEvent(event, tail);
	}

	private TLcdTouchEvent convertTouchEvent(Event aEvent, SwingNode aSwingNode) {
		if (aEvent.getEventType() == TouchEvent.TOUCH_STATIONARY) {
			return null;
		}
		TouchEvent touchEvent = (TouchEvent) aEvent;
		TouchPoint activeFXPoint = touchEvent.getTouchPoint();
		List<TouchPoint> fxTouchPoints = touchEvent.getTouchPoints();
		ArrayList<TLcdTouchPoint> lcdTouchPoints = new ArrayList<>();

		// find out the destination
		Component targetComponent = SwingUtilities.getDeepestComponentAt(aSwingNode.getContent(), Double.valueOf(activeFXPoint.getX()).intValue(),
				Double.valueOf(activeFXPoint.getY()).intValue());
		if (activeFXPoint.getState() == TouchPoint.State.PRESSED) {
			// store the target component for subsequent movements
			fTouchedComponents.put(activeFXPoint.getId(), targetComponent);
		} else {
			targetComponent = fTouchedComponents.getOrDefault(activeFXPoint.getId(), aSwingNode.getContent());
			if (activeFXPoint.getState() == TouchPoint.State.RELEASED) {
				fTouchedComponents.remove(activeFXPoint.getId());
			}
		}

		TLcdTouchPoint lcdPoint = convertTouchPoint(targetComponent, activeFXPoint, convertState(activeFXPoint));
		lcdTouchPoints.add(lcdPoint);

		List<TouchPoint> otherFXPoints = fxTouchPoints.stream().filter(p -> p.getId() != activeFXPoint.getId())
				.collect(Collectors.toList());
		Set<Integer> trackedFingers = new HashSet<>();
		for (TouchPoint p : otherFXPoints) {
			if (trackedFingers.contains(p.getId())) {
//				System.err.println("Ignoring duplicate touch point " + p.getId() + " for event " + p + ". This is most likely a JDK bug.");
			} else {
				if (lcdTouchPoints.size() >= ((TouchEvent) aEvent).getTouchCount()) {
//					System.err.println("Event " + p + " has more points than advertised. Ignoring " + p.getId());
				} else {
					lcdTouchPoints.add(convertTouchPoint(targetComponent, p, TLcdTouchPoint.State.STATIONARY));
					trackedFingers.add(p.getId());
				}
			}
		}
		;
		return new TLcdTouchEvent(TLcdTouchEvent.createTouchEventID(), targetComponent, lcdTouchPoints, 0, "javafx", 0);
	}

	private static TLcdTouchPoint.State convertState(TouchPoint touchPoint) {
		TLcdTouchPoint.State lcdState = null;
		switch (touchPoint.getState()) {
			case PRESSED:
				lcdState = TLcdTouchPoint.State.DOWN;
				break;
			case MOVED:
				lcdState = TLcdTouchPoint.State.MOVED;
				break;
			case STATIONARY:
				lcdState = TLcdTouchPoint.State.STATIONARY;
				break;
			case RELEASED:
				lcdState = TLcdTouchPoint.State.UP;
				break;
			default:
				break;
		}
		return lcdState;
	}

	private static TLcdTouchPoint convertTouchPoint(Component aComponent, TouchPoint touchPoint, TLcdTouchPoint.State aState) {
		//TODO previous implementation
//		Point point = new Point((int) touchPoint.getScreenX(), (int) touchPoint.getScreenY());
//		SwingUtilities.convertPointFromScreen(point, aComponent);
		
		Point point = new Point(Double.valueOf((touchPoint.getPickResult().getIntersectedPoint().getX())).intValue(), 
				Double.valueOf(touchPoint.getPickResult().getIntersectedPoint().getY()).intValue());

		return new TLcdTouchPoint(aState, touchPoint.getId(), point, 0, "", false);
	}
}
