package com.phenix.farmer;

public enum RunningMode {
    LOCAL(0), REMOTE(1);
    private int mode;

    RunningMode(int mode_) {
        mode = mode_;
    }
}
