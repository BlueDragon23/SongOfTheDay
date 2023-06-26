package sotd.adapter;

import sotd.spotify.model.PlaylistTrackObject;

public interface ModelConverter<T> {

    T toRecord(PlaylistTrackObject trackObject);

    PlaylistTrackObject fromRecord(T record);
}
