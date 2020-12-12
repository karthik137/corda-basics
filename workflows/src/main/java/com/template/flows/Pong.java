package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.FlowSession;
import net.corda.core.flows.InitiatedBy;
import net.corda.core.utilities.UntrustworthyData;

@InitiatedBy(PingFLow.class)
public class Pong extends FlowLogic<String> {

    private FlowSession counterPartySession;

    public Pong(FlowSession counterPartySession) {
        this.counterPartySession = counterPartySession;
    }

    @Override
    @Suspendable
    public String call() throws FlowException {

        UntrustworthyData<String> counterPartyData = counterPartySession.receive(String.class);

        counterPartyData.unwrap(data -> {
            assert (data.equals("ping"));
            return true;
        });

        counterPartySession.send("Pong");
        return null;
    }
}
