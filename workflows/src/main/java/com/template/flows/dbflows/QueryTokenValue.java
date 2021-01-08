package com.template.flows.dbflows;

import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.InitiatingFlow;
import net.corda.core.flows.StartableByRPC;
import net.corda.core.utilities.ProgressTracker;

import java.sql.SQLException;

@StartableByRPC
@InitiatingFlow
public class QueryTokenValue extends FlowLogic<Integer> {


    private final ProgressTracker progressTracker = new ProgressTracker();
    private final String token;

    public QueryTokenValue(String token){
        this.token = token;
    }


    @Override
    public Integer call() throws FlowException {
        final CryptoValuesDatabaseService cryptoValuesDatabaseService = getServiceHub().cordaService(CryptoValuesDatabaseService.class);
        Integer val = null;
        try {
            val = cryptoValuesDatabaseService.queryTokenValue(token);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return val;
    }
}
