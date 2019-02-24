The Gateway Project
============

This project created for adapt your system an `API Gateway` pattern. 
You can use this project as a `gateway` for your micro services.
Current state of this project is only redirecting path to related micro services.
For future state, the authentication will be implemented.

For redirection, you should make changes in `application.yml` file.

    gateway:
      config:
        mapping:
          -
            target: /{path.for.gateway}
            uri: {your.service.url}
            


