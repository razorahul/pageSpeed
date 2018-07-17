public class Result {
    private String captchaResult;
    private String kind;
    private String id;
    private String responseCode;
    private String title;
    private DeviceResult desktop;
    private DeviceResult mobile;

    public String getCaptchaResult() {
        return captchaResult;
    }

    public void setCaptchaResult(String captchaResult) {
        this.captchaResult = captchaResult;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DeviceResult getDesktop() {
        return desktop;
    }

    public void setDesktop(DeviceResult desktop) {
        this.desktop = desktop;
    }

    public DeviceResult getMobile() {
        return mobile;
    }

    public void setMobile(DeviceResult mobile) {
        this.mobile = mobile;
    }
}
