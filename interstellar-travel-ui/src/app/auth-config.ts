import { NbPasswordAuthStrategy, NbAuthSimpleToken } from "@nebular/auth";
import { environment } from "src/environments/environment";

export const AuthConfig = {
  strategies: [
    NbPasswordAuthStrategy.setup({
      name: "email",
      baseEndpoint: environment.authUrl,
      token: {
        class: NbAuthSimpleToken,
        key: "token"
      },
      login: {
        alwaysFail: false,
        endpoint: "/auth/login",
        method: "post",
        requireValidToken: false,
        redirect: {
          success: environment.homePath,
          failure: null
        },
        defaultErrors: [
          "Login/Email combination is not correct, please try again."
        ],
        defaultMessages: [
          "You have been successfully logged in. Redirecting to home."
        ]
      }
    })
  ],
  forms: {
    login: {
      redirectDelay: 200,
      strategy: "email", // strategy id key.
      rememberMe: false, // whether to show or not the `rememberMe` checkbox
      showMessages: {
        success: true,
        error: true
      }
    }
  }
};
