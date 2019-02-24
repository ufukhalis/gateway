The Gateway Project
============

This project created for adapt your system an `API Gateway` pattern. 
You can use this project as a `gateway` for your micro services.
Current state of this project is only redirecting path to related micro services.

For redirection, you should make changes in `application.yml` file.

    gateway:
      config:
        mapping:
          -
            target: /{path.for.gateway}
            uri: {your.service.url}
            
Note
------------
This project is still under development.

This project uses `Spring-WebFlux` and `vavr.io`.

For more information about vavr.io, check the site http://vavr-io.github.io


License
----------
All code in this repository is licensed under the Apache License, Version 2.0. See LICENCE.