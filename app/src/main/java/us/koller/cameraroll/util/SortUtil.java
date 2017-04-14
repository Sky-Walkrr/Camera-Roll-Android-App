package us.koller.cameraroll.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import us.koller.cameraroll.data.Album;
import us.koller.cameraroll.data.File_POJO;

public class SortUtil {

    //interface, implemented by Album & AlbumItem, to sort them
    public interface Sortable {
        String getName();

        long getDate(Activity context);

        String getPath();
    }

    public static final int BY_DATE = 1;
    public static final int BY_NAME = 2;
    public static final int BY_SIZE = 3;

    public static ArrayList<? extends Sortable> sortAlbums(Activity context, ArrayList<Album> albums, int by) {
        switch (by) {
            case BY_NAME:
            case BY_DATE:
                return sort(context, albums, by);
            case BY_SIZE:
                // Sorting
                Collections.sort(albums, new Comparator<Album>() {
                    @Override
                    public int compare(Album a1, Album a2) {
                        if (a1 != null && a2 != null) {
                            Integer a1_size = a1.getAlbumItems().size();
                            Integer a2_size = a2.getAlbumItems().size();
                            return a2_size.compareTo(a1_size);
                        }
                        return 0;
                    }
                });
                return albums;
        }
        return albums;
    }

    public static File_POJO sortFiles(Activity context, File_POJO files) {
        sort(context, files.getChildren(), BY_NAME);

        return files;
    }

    public static ArrayList<? extends Sortable> sort(Activity context, ArrayList<? extends Sortable> sortables, int by) {
        switch (by) {
            case BY_NAME:
                return sortByName(sortables);
            case BY_DATE:
                return sortByDate(context, sortables);
            case BY_SIZE:
                return sortByDate(context, sortables);
        }
        return sortables;
    }

    public static ArrayList<? extends Sortable> sortByName(ArrayList<? extends Sortable> sortables) {
        // Sorting
        Collections.sort(sortables, new Comparator<Sortable>() {
            @Override
            public int compare(Sortable s1, Sortable s2) {
                if (s1 != null && s2 != null) {
                    return s1.getName().compareTo(s2.getName());
                }
                return 0;
            }
        });
        return sortables;
    }

    public static ArrayList<? extends Sortable> sortByDate(final Activity context, ArrayList<? extends Sortable> sortables) {
        // Sorting
        Collections.sort(sortables, new Comparator<Sortable>() {
            @Override
            public int compare(Sortable s1, Sortable s2) {
                if (s1 != null && s2 != null) {
                    Long l1 = s1.getDate(context);
                    Long l2 = s2.getDate(context);
                    return l2.compareTo(l1);
                }
                return 0;
            }
        });
        return sortables;
    }
}
