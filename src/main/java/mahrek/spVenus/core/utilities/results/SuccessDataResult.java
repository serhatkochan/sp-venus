package mahrek.spVenus.core.utilities.results;

public class SuccessDataResult<T> extends DataResult<T> {
    // cevap bilgisi true dönecek
    public SuccessDataResult(T data) {
        super(data, true);
    }
    public SuccessDataResult(T data, String message) {
        super(data, true, message);
    }
    public SuccessDataResult() {
        super(null, true); // belki data gelmeyebilir
    }
    public SuccessDataResult(String message) {
        super(null, true, message); // data gelmeyip mesaj gonderilmek istenebilir
    }
}
