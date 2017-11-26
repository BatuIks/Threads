import java.util.Collection;
import java.util.List;

public interface ChmuraAbst {
    public abstract Byt ustaw(int x, int y) throws NiebytException;
    public abstract void przestaw(Collection<Byt> byty, int dx, int dy) throws NiebytException, InterruptedException;
    public abstract void kasuj(Byt byt) throws NiebytException;
    public abstract int[] miejsce(Byt byt);
}
