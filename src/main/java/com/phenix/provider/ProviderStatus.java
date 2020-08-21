package com.phenix.provider;

import lombok.Getter;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public enum ProviderStatus {
    Disconnected(1), Connecting(2), Connected(3), Unknown(-1);

    @Getter
    private int value;

    ProviderStatus(int value_) {
        value = value_;
    }

    public static ProviderStatus of(int value_) {
        ProviderStatus s = Unknown;
        switch (value_) {
            case 1 : s = Disconnected; break;
            case 2 : s = Connecting; break;
            case 3 : s = Connected; break;
            case 4 : break;
        }
        return s;
    }

    private void writeObject(ObjectOutputStream s) throws java.io.IOException {
        ObjectOutputStream.PutField putFields = s.putFields();
        putFields.put("value", value);
        s.writeFields();
    }

    private void readObject(ObjectInputStream s_) throws java.io.IOException, ClassNotFoundException {
        ObjectInputStream.GetField getFields = s_.readFields();
        value = getFields.get("value", -1);
    }
}
