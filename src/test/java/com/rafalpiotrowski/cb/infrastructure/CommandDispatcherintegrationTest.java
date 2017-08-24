package com.rafalpiotrowski.cb.infrastructure;

import com.rafalpiotrowski.cb.infrastructure.config.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class CommandDispatcherintegrationTest {

    @Autowired
    private CommandDispatcher commandDispatcher;

    @Test
    public void shouldSayHiToUser() {
        // given
        String command = "HI, I'M rafal";
        SessionData session = new SessionData(new SessionId());

        // when
        String response = commandDispatcher.dispatch(command, session);

        // then
        assertThat(response).isEqualTo("HI rafal");
    }

    @Test
    public void shouldSayByeToUser() {
        // given
        String command = "BYE MATE!";
        SessionData session = new SessionData(new SessionId());
        session.put(SessionData.Key.USER_NAME, "rafal");

        // when
        String response = commandDispatcher.dispatch(command, session);

        // then
        assertThat(response).contains("BYE rafal, WE SPOKE FOR");
        assertThat(response).contains(" MS");
    }

    @Test
    public void shouldReturnThatCommandIsUnknown() {
        // given
        String command = "FOO BAR";
        SessionData session = new SessionData(new SessionId());

        // when
        String response = commandDispatcher.dispatch(command, session);

        // then
        assertThat(response).isEqualTo("SORRY, I DIDN'T UNDERSTAND THAT");
    }

    @Test
    public void shouldAddNode() {
        // given
        String command = "ADD NODE FOO-1";
        SessionData session = new SessionData(new SessionId());

        // when
        String response = commandDispatcher.dispatch(command, session);

        // then
        assertThat(response).isEqualTo("NODE ADDED");
    }

    @Test
    public void shouldNotAddNode() {
        // given
        String command = "ADD NODE FOO-2";
        SessionData session = new SessionData(new SessionId());
        commandDispatcher.dispatch(command, session);

        // when
        String response = commandDispatcher.dispatch(command, session);

        // then
        assertThat(response).isEqualTo("ERROR: NODE ALREADY EXISTS");
    }

    @Test
    public void shouldAddEdge() {
        // given
        String command = "ADD EDGE FOO-3 BAR-3 10";
        SessionData session = new SessionData(new SessionId());
        commandDispatcher.dispatch("ADD NODE FOO-3", session);
        commandDispatcher.dispatch("ADD NODE BAR-3", session);

        // when
        String response = commandDispatcher.dispatch(command, session);

        // then
        assertThat(response).isEqualTo("EDGE ADDED");
    }

    @Test
    public void shouldNotAddEdge() {
        // given
        String command = "ADD EDGE FOO-4 BAR-4 10";
        SessionData session = new SessionData(new SessionId());

        // when
        String response = commandDispatcher.dispatch(command, session);

        // then
        assertThat(response).isEqualTo("ERROR: NODE NOT FOUND");
    }

    @Test
    public void shouldRemoveNode() {
        // given
        String command = "REMOVE NODE FOO-5";
        SessionData session = new SessionData(new SessionId());
        commandDispatcher.dispatch("ADD NODE FOO-5", session);

        // when
        String response = commandDispatcher.dispatch(command, session);

        // then
        assertThat(response).isEqualTo("NODE REMOVED");
    }

    @Test
    public void shouldNotRemoveNode() {
        // given
        String command = "REMOVE NODE FOO-6";
        SessionData session = new SessionData(new SessionId());

        // when
        String response = commandDispatcher.dispatch(command, session);

        // then
        assertThat(response).isEqualTo("ERROR: NODE NOT FOUND");
    }

    @Test
    public void shouldRemoveEdge() {
        // given
        String command = "REMOVE EDGE FOO-7 BAR-7";
        SessionData session = new SessionData(new SessionId());
        commandDispatcher.dispatch("ADD NODE FOO-7", session);
        commandDispatcher.dispatch("ADD NODE BAR-7", session);
        commandDispatcher.dispatch("ADD EDGE FOO-7 BAR-7 1", session);

        // when
        String response = commandDispatcher.dispatch(command, session);

        // then
        assertThat(response).isEqualTo("EDGE REMOVED");
    }

    @Test
    public void shouldNotRemoveEdgeAndReturnError() {
        // given
        String command = "REMOVE EDGE FOO-8 BAR-8";
        SessionData session = new SessionData(new SessionId());
        commandDispatcher.dispatch("ADD NODE FOO-8", session);

        // when
        String response = commandDispatcher.dispatch(command, session);

        // then
        assertThat(response).isEqualTo("ERROR: NODE NOT FOUND");
    }

    @Test
    public void shouldNotRemoveEdgeAndDoNotReturnError() {
        // given
        String command = "REMOVE EDGE FOO-9 BAR-9";
        SessionData session = new SessionData(new SessionId());
        commandDispatcher.dispatch("ADD NODE FOO-9", session);
        commandDispatcher.dispatch("ADD NODE BAR-9", session);

        // when
        String response = commandDispatcher.dispatch(command, session);

        // then
        assertThat(response).isEqualTo("EDGE REMOVED");
    }

    @Test
    public void shouldFindShortestPath() {
        // given
        String command = "SHORTEST PATH FOO-10 TEST-10";
        SessionData session = new SessionData(new SessionId());
        commandDispatcher.dispatch("ADD NODE FOO-10", session);
        commandDispatcher.dispatch("ADD NODE BAR-10", session);
        commandDispatcher.dispatch("ADD NODE TEST-10", session);
        commandDispatcher.dispatch("ADD EDGE FOO-10 BAR-10 2", session);
        commandDispatcher.dispatch("ADD EDGE BAR-10 TEST-10 4", session);
        commandDispatcher.dispatch("ADD EDGE BAR-10 TEST-10 1", session);

        // when
        String response = commandDispatcher.dispatch(command, session);

        // then
        assertThat(response).isEqualTo("3");
    }

    @Test
    public void shouldNotFindShortestPath() {
        // given
        String command = "SHORTEST PATH FOO-11 TEST-11";
        SessionData session = new SessionData(new SessionId());

        // when
        String response = commandDispatcher.dispatch(command, session);

        // then
        assertThat(response).isEqualTo("ERROR: NODE NOT FOUND");
    }

    @Test
    public void shouldReturnNodesCloserThan() {
        // given
        String command = "CLOSER THAN 5 N-01";
        SessionData session = new SessionData(new SessionId());
        commandDispatcher.dispatch("ADD NODE N-01", session);
        commandDispatcher.dispatch("ADD NODE N-02", session);
        commandDispatcher.dispatch("ADD NODE N-03", session);
        commandDispatcher.dispatch("ADD NODE N-04", session);
        commandDispatcher.dispatch("ADD EDGE N-01 N-02 1", session);
        commandDispatcher.dispatch("ADD EDGE N-02 N-03 2", session);
        commandDispatcher.dispatch("ADD EDGE N-03 N-04 3", session);

        // when
        String response = commandDispatcher.dispatch(command, session);

        // then
        assertThat(response).isEqualTo("N-02,N-03");
    }

    @Test
    public void shouldNotReturnNodesCloserThan() {
        // given
        String command = "CLOSER THAN 5 NODE-01";
        SessionData session = new SessionData(new SessionId());

        // when
        String response = commandDispatcher.dispatch(command, session);

        // then
        assertThat(response).isEqualTo("ERROR: NODE NOT FOUND");
    }
}