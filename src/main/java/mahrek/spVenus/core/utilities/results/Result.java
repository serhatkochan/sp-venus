package mahrek.spVenus.core.utilities.results;

public class Result {
    private boolean success;
    private String message;

    public Result(Boolean success) {
        this.success = success;
    }
    public Result(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean isSuccess() { // cevab bilgisine erişmek için
        return this.success;
    }
    public String getMessage() { // mesaj bilgisine erişmek için
        return this.message;
    }
}
