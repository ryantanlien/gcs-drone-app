package sg.gov.dsta.thickdemo.plugins.welcome;

import org.pf4j.ExtensionPoint;

public interface Greeting extends ExtensionPoint {
    String getGreeting();
}


