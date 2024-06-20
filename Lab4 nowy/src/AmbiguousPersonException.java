public class AmbiguousPersonException extends Exception
{
    public AmbiguousPersonException(String name)
    {
        super("Występuje ponad jedna osoba o tym samym imieniu i nazwisku:" + name);
    }
}
