package com.example.mystylistmobile.dto.response;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterV2ResponseDTO {

    @SerializedName("user")
    private UserResponseDTO user;
    @SerializedName("tokens")
    private TokenResponse tokens;

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public void setTokens(TokenResponse tokens) {
        this.tokens = tokens;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public TokenResponse getTokens() {
        return tokens;
    }

    @Data
    @Builder
    public static class TokenResponse {
        @SerializedName("access")
        private TokenDTO access;

        @SerializedName("refresh")
        private TokenDTO refresh;

        public TokenDTO getAccess() {
            return access;
        }

        public void setAccess(TokenDTO access) {
            this.access = access;
        }

        public TokenDTO getRefresh() {
            return refresh;
        }

        public void setRefresh(TokenDTO refresh) {
            this.refresh = refresh;
        }
    }


}
