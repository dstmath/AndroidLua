package cm.lua.moon;

public final class LuaRequest {
    public Object[] args;
    public String luaScript;

    static final class Builder {
        private Object[] args;
        private String luaScript;

        public Builder() {
        }

        public final Builder args(Object[] objArr) {
            this.args = objArr;
            return this;
        }

        public final LuaRequest build() {
            if (this.args == null) {
                this.args = new Object[0];
            }
            if (this.luaScript == null) {
                this.luaScript = "";
            }
            return new LuaRequest(this);
        }

        public final Builder luaScript(String str) {
            this.luaScript = str;
            return this;
        }
    }

    public LuaRequest(Builder builder) {
        this.luaScript = builder.luaScript;
        this.args = builder.args;
    }
}
