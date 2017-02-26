package com.tos.utils;

import com.google.gson.annotations.Expose;

public class Properties {
	@Expose
	public String cmd_id;
	@Expose
    public String result_type;// "str",
	@Expose
	public String device_id;
	@Expose
    public String cmd_data;
	@Expose
    public String device_type;//"light"
	@Expose
    public String expect_result;//: "test",
	@Expose
    public String operator;//": "eq",
	@Expose
    public String result_name;//": "res"
	@Expose
    public int time;
}
