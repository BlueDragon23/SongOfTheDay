FROM openjdk:17
# Note, this will copy secrets file, git history, probably manage this better
COPY . /usr/src/SongOfTheDay
WORKDIR /usr/src/SongOfTheDay
# Should run as non-root user
RUN ./gradlew run