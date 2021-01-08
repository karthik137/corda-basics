package com.template.flows.scheduleactivity;

import net.corda.core.contracts.SchedulableState;
import net.corda.core.contracts.ScheduledActivity;
import net.corda.core.contracts.StateRef;
import net.corda.core.flows.FlowLogicRefFactory;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.serialization.ConstructorForDeserialization;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class HeartState implements SchedulableState {
    private final Party me;
    private final Instant nextActivityTime;

    @ConstructorForDeserialization
    public HeartState(Party me, Instant nextActivityTime){
        this.me = me;
        this.nextActivityTime = nextActivityTime;
    }

    public Party getMe() {
        return me;
    }


    @Nullable
    @Override
    public ScheduledActivity nextScheduledActivity(@NotNull StateRef thisStateRef, @NotNull FlowLogicRefFactory flowLogicRefFactory) {

        return new ScheduledActivity(flowLogicRefFactory.create("net.corda.samples.heartbeat.flows.HeartbeatFlow", thisStateRef), nextActivityTime);
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(me);
    }
}
