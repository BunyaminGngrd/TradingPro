package trading.pro.common;

public class GnlEnumTypes {
    public enum ResponseCode {
        SUCCESS("200"),
        ERROR("500"),
        PARTIAL_SUCCESS("100");

        private final String value;

        ResponseCode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ResponseMessage {
        BUY,
        SELL,
        NO_SIGNAL
    }

    public enum Response {
        TRUE,
        FALSE
    }
}
