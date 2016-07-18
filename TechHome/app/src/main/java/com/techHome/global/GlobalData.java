package com.techHome.global;

import android.app.Application;

import com.techHome.dto.OrderDTO;

import java.util.List;

/**
 * Created by Harsh on 7/19/2016.
 */
public class GlobalData extends Application {
    private List<OrderDTO> orderDTOs;


    public List<OrderDTO> getOrderDTOs() {
        return orderDTOs;
    }

    public void setOrderDTOs(List<OrderDTO> orderDTOs) {
        this.orderDTOs = orderDTOs;
    }

}
