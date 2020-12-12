package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.utilities.UntrustworthyData;

@InitiatingFlow
@StartableByRPC
public class PingFLow extends FlowLogic<String> {


    private Party counterparty;

    public PingFLow(Party counterparty) {
        this.counterparty = counterparty;
    }

    @Override
    @Suspendable
    public String call() throws FlowException {

        FlowSession counterPartySession = initiateFlow(counterparty);
        UntrustworthyData<String> counterPartyData = counterPartySession.sendAndReceive(String.class, "ping");

        counterPartyData.unwrap(data -> {
            assert(data.equals("pong"));
            return true;
        });

        return null;
    }
}
