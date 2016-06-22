package repartizare;

import javax.faces.bean.ManagedBean;

import java.io.IOException;

import static ro.info.uaic.opt.Application.*;

/**
 * Created by Iulia-PC on 6/21/2016.
 */
@ManagedBean(name="repartitionCtrl")
public class RepartitionController {

    public void allocate(){
        try{
            courseAllocation();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}


