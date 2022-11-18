FROM gitpod/workspace-postgresql

RUN brew install scala

RUN brew install sbt

RUN brew install scalaenv

RUN scalaenv install scala-2.13.6 && scalaenv global scala-2.13.6