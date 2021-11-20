package sotd.adapter;

import sotd.spotify.model.TrackObject;

public interface ModelConverter<T> {

    T toRecord(TrackObject trackObject);

    TrackObject fromRecord(T record);
}
