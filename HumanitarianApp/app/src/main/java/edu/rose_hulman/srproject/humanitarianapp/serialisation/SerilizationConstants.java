package edu.rose_hulman.srproject.humanitarianapp.serialisation;

import edu.rose_hulman.srproject.humanitarianapp.localdata.ApplicationWideData;

/**
 * These are constants used by the serilization and deserilization. Some are also needed for initial
 * object creation, to add typing to the IDs.
 */
public class SerilizationConstants {
    public static final int TYPE_LOCATION = 48;
    public static final int CREATER_LOCATION = 32;
    public static final int COUNTER_LOCATION = 0;

    //Leaving PROJECT_NUM as the operation rather than just 0 to follow the pattern.
    public static final long PROJECT_NUM = ((long) 0);
    public static final long GROUP_NUM = ((long) 1);
    public static final long PERSON_NUM = ((long) 2);
    public static final long SHIPMENT_NUM = ((long) 3);
    public static final long LOCATION_NUM = ((long) 4);
    public static final long CHECKLIST_NUM = ((long) 5);

    public static long generateID(long objectEnum) {
        long value = objectEnum << TYPE_LOCATION;
        value = value | ((long) ApplicationWideData.userID) << CREATER_LOCATION;
        value = value | ((long) ApplicationWideData.createdObjectCounter) << COUNTER_LOCATION;
        ApplicationWideData.createdObjectCounter ++;
        return value;
    }

}
