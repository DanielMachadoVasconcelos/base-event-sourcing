package br.ead.home.infrastructure;

import br.ead.home.commands.BaseCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AccountCommandDispatcher implements CommandDispatcher {

    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());
        if(handlers == null || handlers.size() == 0) {
            throw new IllegalStateException("No command handler is registered!");
        }
        if (handlers.size() > 1) {
            throw new IllegalStateException("Can't send to more then one handler!");
        }
        handlers.get(0).handle(command);
    }
}
