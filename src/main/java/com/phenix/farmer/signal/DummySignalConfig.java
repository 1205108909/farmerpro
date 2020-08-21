package com.phenix.farmer.signal;

import com.phenix.data.IEngineConfig;
import com.phenix.exception.NotSupportedException;
import org.jdom2.Element;

public class DummySignalConfig implements IEngineConfig {
    @Override
    public boolean readConfig(Element e_) {
        return true;
    }

    @Override
    public int hashCode() {
        throw new NotSupportedException(getClass().getName() + " is not allowed to be used as hash key");
    }
}
