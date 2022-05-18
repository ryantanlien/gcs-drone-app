package gov.dsta.thickdemo.welcome;

import org.pf4j.ExtensionPoint;

public interface Greeting extends ExtensionPoint {
    String getGreeting();
}


