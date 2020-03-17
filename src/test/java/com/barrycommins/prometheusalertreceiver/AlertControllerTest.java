package com.barrycommins.prometheusalertreceiver;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AlertControllerTest {

    @Test
    public void testMessageForwarded() {
        AlertForwarder forwarder = mock(AlertForwarder.class);
        AlertController controller = new AlertController(forwarder);

        controller.alert(new PrometheusMessage());
        verify(forwarder).send(any(PrometheusMessage.class));
    }

}