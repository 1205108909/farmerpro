package com.phenix.farmer.command;

import com.phenix.admin.AdminRequestFactory;
import com.phenix.admin.AdminServiceIoHandler;
import lombok.Getter;

public class FarmerCommandFactory extends AdminRequestFactory {
    private final static FarmerCommandFactory INSTANCE = new FarmerCommandFactory();
    @Getter
    private AdminServiceIoHandler handler;

    private FarmerCommandFactory() {
        super();
        handler = new AdminServiceIoHandler(this);
    }

    public static FarmerCommandFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public void register() {
        register(new SignalListAdmin());
        register(new SignalOrderListAdmin());
        register(new EngineStateAdmin());
        register(new DataOrderBookListAdmin());
        register(new SignalEvaluationAdmin());
        register(new EnginePLAdmin());
        register(new SignalOrderCloseAdmin());
        register(new EngineStateManageAdmin());
        register(new DataCachePrint());
        register(new DataCacheManageAdmin());
        register(new SignalOrderPublishAdmin());
    }
}
