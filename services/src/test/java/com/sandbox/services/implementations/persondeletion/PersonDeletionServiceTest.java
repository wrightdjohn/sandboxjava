package com.sandbox.services.implementations.persondeletion;

import com.sandbox.allsystemutils.exceptions.InvalidServiceRequest;
import com.sandbox.services.api.dao.Deleter;
import com.sandbox.services.core.BizId;
import com.sandbox.services.core.StringBizId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;

public class PersonDeletionServiceTest {

    PersonDeletionService service;
    Deleter deleter;

    @BeforeEach
    public void setup() {
        deleter = Mockito.mock(Deleter.class);
        service = new PersonDeletionService(deleter);
    }

    @Test
    public void whenValidRequest_expectCallToDeleterWithId() {
        PersonDeletionRequest request = new PersonDeletionRequest(new StringBizId("idStr"));
        ArgumentCaptor<BizId> argumentCaptor = ArgumentCaptor.forClass(BizId.class);
        doNothing().when(deleter).delete(argumentCaptor.capture());
        PersonDeletionResponse response = service.executeRequest(request);
        BizId id = argumentCaptor.getValue();
        assertThat(id.asText()).isEqualTo("idStr");
    }

    @Test
    public void whenBadRequest_expectException() {
        PersonDeletionRequest request = new PersonDeletionRequest(null);

        assertThatThrownBy(() -> {
            service.executeRequest(request);
        }).isInstanceOf(InvalidServiceRequest.class)
                .hasMessage("Id passed for deletion was null");
    }
}
