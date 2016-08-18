import java.util.Date;
import java.util.List;

public interface Service {

    public void doHardWork();

    @ Cache (cacheType = CacheType.FILE, fileNamePrefix = "data", zip = true, identityBy = {String.class, double.class})
    int doHardWork(String item, double value, Date date);

    @ Cache (cacheType = CacheType.IN_MEMORY, listList = 100_000)
    int doHardWork(String item);

}
