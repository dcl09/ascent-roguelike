package ascent.view.game;

import ascent.gui.GUI;
import ascent.model.entities.*;
import ascent.model.entities.monster.Monster;
import ascent.model.game.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class EntityViewerTest {
    static Stream<Arguments> entityViewerProvider(){
        return Stream.of(
                Arguments.of(new PlayerViewer(), mock(Player.class)),
                Arguments.of (new MonsterViewer(), mock(Monster.class)),
                Arguments.of (new WallViewer(), mock(Wall.class)),
                Arguments.of (new ChestViewer(), mock(Chest.class)),
                Arguments.of (new StairsViewer(), mock(Stairs.class)),
                Arguments.of (new DoorViewer(), mock(Door.class))
        );
    }

    @ParameterizedTest
    @MethodSource("entityViewerProvider")
    <T extends Entity> void testDraw(EntityViewer<T> viewer, T entity) {
        GUI gui = mock(GUI.class);
        Position position = new Position(1,1);
        when(entity.getPosition()).thenReturn(position);
        viewer.draw(entity, gui);
        verify(gui, times(1)).drawChar(eq(1), eq(1), anyChar(), any());
    }
}
