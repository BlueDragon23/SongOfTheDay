package sotd.adapter;

import sotd.common.model.Song;

public interface ModelConverter<T> {

    T toRecord(Song trackObject);

    Song fromRecord(T record);
}
