FROM openjdk:14
# Note, this will copy secrets file, git history, probably manage this better
COPY . /usr/src/SongOfTheDay
WORKDIR /usr/src/myapp
# Should run as non-root user
RUN ./gradlew run