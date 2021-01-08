package com.template.flows.dbflows;

import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.InitiatingFlow;
import net.corda.core.flows.StartableByRPC;
import net.corda.core.utilities.ProgressTracker;

import java.sql.SQLException;

@InitiatingFlow
@StartableByRPC
public class AddTokenValue extends FlowLogic<Void> {

    private final ProgressTracker progressTracker = new ProgressTracker();
    private final String token;
    private final Integer value;


    public AddTokenValue(String token, Integer value) {
        this.token = token;
        this.value = value;
    }

    @Override
    public ProgressTracker getProgressTracker(){
        return progressTracker;
    }


    @Override
    public Void call() throws FlowException {

        final CryptoValuesDatabaseService cryptoValuesDatabaseService = getServiceHub().cordaService(CryptoValuesDatabaseService.class);

        try{
            cryptoValuesDatabaseService.addtokenValue(token,value);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }
}
