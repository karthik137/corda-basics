package com.template.flows.observersflow;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import net.corda.core.node.StatesToRecord;

@InitiatedBy(TradeAndReport.class)
public class TradeAndReportResponder extends FlowLogic<Void> {
    private final FlowSession counterPartySesion;

    public TradeAndReportResponder(FlowSession counterPartySesion) {
        this.counterPartySesion = counterPartySesion;
    }

    @Override
    @Suspendable
    public Void call() throws FlowException {
        subFlow(new ReceiveFinalityFlow(counterPartySesion, null, StatesToRecord.ALL_VISIBLE));
        return null;
    }

}
