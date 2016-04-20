/* ------------------------------------------------------------------------------
 *
 *  # Echarts - columns and waterfalls
 *
 *  Columns and waterfalls chart configurations
 *
 *  Version: 1.0
 *  Latest update: August 1, 2015
 *
 * ---------------------------------------------------------------------------- */

$(function () {

    // Set paths
    // ------------------------------

    require.config({
        paths: {
            echarts: 'assets/js/plugins/visualization/echarts'
        }
    });


    // Configuration
    // ------------------------------

    require(
        [
            'echarts',
            'echarts/theme/limitless',
            'echarts/chart/bar',
            'echarts/chart/line'
        ],


        // Charts setup
        function (ec, limitless) {


            // Initialize charts   
            // ------------------------------

            var basic_columns = ec.init(document.getElementById('basic_columns'), limitless);
            var basic_columns1 = ec.init(document.getElementById('basic_columns1'), limitless);
            var food_consumption = ec.init(document.getElementById('food_consumption'), limitless);
            var clothing_consumption = ec.init(document.getElementById('clothing_consumption'), limitless);
            var food_sales = ec.init(document.getElementById('food_sales'), limitless);
            var clothing_sales = ec.init(document.getElementById('clothing_sales'), limitless);
            // Charts setup
            // ------------------------------


            //
            // Basic columns options
            //
            //人流量排行
            basic_columns_options = {

                // Setup grid
                grid: {
                    x: 40,
                    x2: 40,
                    y: 35,
                    y2: 25
                },

                // Add tooltip
                tooltip: {
                    trigger: 'axis'
                },

                // Add legend
                legend: {
                    data: ['2016MADCon搜索分享会-深圳站', '创世纪—“投”脑风暴—深圳站【聚募联盟全国巡讲No.2】']
                },

                // Enable drag recalculate
                calculable: true,

                // Horizontal axis
                xAxis: [{
                    type: 'category',
                    data: ['汉拿山韩式烤肉', '马克华菲', ' 汉堡王', '星巴克', '哈根达斯', '麦当劳', '满记甜品', '巴菲牛排', '泰国食尚', '伊斯小麦', '上古咖啡', '大牛火锅']
                }],

                // Vertical axis
                yAxis: [{
                    type: 'value'
                }],

                // Add series
                series: [
                    {
                        name: '2016MADCon搜索分享会-深圳站',
                        type: 'bar',
                        data: [1166, 1188, 1300,1200, 2010, 600, 1800, 1000, 2120, 768, 1643, 1220],
                        itemStyle: {
                            normal: {
                                label: {
                                    show: true,
                                    textStyle: {
                                        fontWeight: 500
                                    }
                                },
                                color: "rgb(191, 191, 0)"
                            }
                        },
                        markLine: {
                            data: [{type: 'average', name: 'Average'}]
                        }
                    },
                    {
                        name: '创世纪—“投”脑风暴—深圳站【聚募联盟全国巡讲No.2】',
                        type: 'bar',
                        data: [1622, 1355, 1422,1836, 1800, 800, 1685, 1500, 2500, 800, 1888, 1444],
                        itemStyle: {
                            normal: {
                                label: {
                                    show: true,
                                    textStyle: {
                                        fontWeight: 500
                                    }
                                },
                                color: "rgb(0, 255, 127)"
                            }
                        },
                        markLine: {
                            data: [{type: 'average', name: 'Average'}]
                        }
                    }
                ]
            };
            //餐饮消费能力排行
            food_consumption_options = {

                    // Setup grid
                    grid: {
                        x: 40,
                        x2: 40,
                        y: 35,
                        y2: 25
                    },

                    // Add tooltip
                    tooltip: {
                        trigger: 'axis'
                    },

                    // Add legend
                    legend: {
                        data: ['2016MADCon搜索分享会-深圳站', '创世纪—“投”脑风暴—深圳站【聚募联盟全国巡讲No.2】']
                    },

                    // Enable drag recalculate
                    calculable: true,

                    // Horizontal axis
                    xAxis: [{
                        type: 'category',
                        data: ['汉拿山韩式烤肉', '马克华菲', ' 汉堡王', '星巴克', '哈根达斯', '麦当劳', '满记甜品', '巴菲牛排', '泰国食尚', '伊斯小麦', '上古咖啡', '大牛火锅']
                    }],

                    // Vertical axis
                    yAxis: [{
                        type: 'value'
                    }],

                    // Add series
                    series: [
                        {
                            name: '2016MADCon搜索分享会-深圳站',
                            type: 'bar',
                            data: [1200, 1488, 900,1500, 1888, 1600, 800, 700, 2410, 1768, 643, 220],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true,
                                        textStyle: {
                                            fontWeight: 500
                                        }
                                    },
                                    color: "rgb(255, 127, 0)"
                                }
                            },
                            markLine: {
                                data: [{type: 'average', name: 'Average'}]
                            }
                        },
                        {
                            name: '创世纪—“投”脑风暴—深圳站【聚募联盟全国巡讲No.2】',
                            type: 'bar',
                            data: [622, 1355, 422,1836, 800, 1800, 685, 500, 1950, 1800, 888, 444],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true,
                                        textStyle: {
                                            fontWeight: 500
                                        }
                                    },
                                    color: "rgb(170, 255, 255)"
                                }
                            },
                            markLine: {
                                data: [{type: 'average', name: 'Average'}]
                            }
                        }
                    ]
                };
            //餐饮销量排行
            food_sales_options = {

                    // Setup grid
                    grid: {
                        x: 40,
                        x2: 40,
                        y: 35,
                        y2: 25
                    },

                    // Add tooltip
                    tooltip: {
                        trigger: 'axis'
                    },

                    // Add legend
                    legend: {
                        data: ['2016MADCon搜索分享会-深圳站', '创世纪—“投”脑风暴—深圳站【聚募联盟全国巡讲No.2】']
                    },

                    // Enable drag recalculate
                    calculable: true,

                    // Horizontal axis
                    xAxis: [{
                        type: 'category',
                        data: ['汉拿山韩式烤肉', '马克华菲', ' 汉堡王', '星巴克', '哈根达斯', '麦当劳', '满记甜品', '巴菲牛排', '泰国食尚', '伊斯小麦', '上古咖啡', '大牛火锅']
                    }],

                    // Vertical axis
                    yAxis: [{
                        type: 'value'
                    }],

                    // Add series
                    series: [
                        {
                            name: '2016MADCon搜索分享会-深圳站',
                            type: 'bar',
                            data: [966, 1288, 300,800, 1010, 1600, 800, 1380, 1120, 1768, 643, 520],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true,
                                        textStyle: {
                                            fontWeight: 500
                                        }
                                    }
                                }
                            },
                            markLine: {
                                data: [{type: 'average', name: 'Average'}]
                            }
                        },
                        {
                            name: '创世纪—“投”脑风暴—深圳站【聚募联盟全国巡讲No.2】',
                            type: 'bar',
                            data: [762, 1355, 400,1336, 900, 1800, 685, 780, 400, 1800, 1128, 888],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true,
                                        textStyle: {
                                            fontWeight: 500
                                        }
                                    }
                                }
                            },
                            markLine: {
                                data: [{type: 'average', name: 'Average'}]
                            }
                        }
                    ]
                };
            
            basic_columns_options1 = {

                    // Setup grid
                    grid: {
                        x: 40,
                        x2: 40,
                        y: 35,
                        y2: 25
                    },

                    // Add tooltip
                    tooltip: {
                        trigger: 'axis'
                    },

                    // Add legend
                    legend: {
                    	data: ['2016MADCon搜索分享会-深圳站', '创世纪—“投”脑风暴—深圳站【聚募联盟全国巡讲No.2】']
                    },

                    // Enable drag recalculate
                    calculable: true,

                    // Horizontal axis
                    xAxis: [{
                        type: 'category',
                        data: ['MUJI', 'hotwind', 'Charles&Keith', 'H&M', '迪诗芬', 'Jack&Jones', 'spur韩国手工鞋', 'Mango', 'LV', '5cm', '曼古银', '索菲娅SAFIYA']
                    }],

                    // Vertical axis
                    yAxis: [{
                        type: 'value'
                    }],

                    // Add series
                    series: [
                        {
                        	name: '2016MADCon搜索分享会-深圳站',
                            type: 'bar',
                            data: [1622, 1355, 1422,1836, 1800, 800, 1685, 1500, 2500, 800, 1888, 1444],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true,
                                        textStyle: {
                                            fontWeight: 500
                                        }
                                    }
                                }
                            },
                            markLine: {
                                data: [{type: 'average', name: 'Average'}]
                            }
                        },
                        {
                        	name: '创世纪—“投”脑风暴—深圳站【聚募联盟全国巡讲No.2】',
                            type: 'bar',
                            data: [1166, 1188, 1300,1200, 2010, 600, 1800, 1000, 2120, 768, 1643, 1220],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true,
                                        textStyle: {
                                            fontWeight: 500
                                        }
                                    }
                                }
                            },
                            markLine: {
                                data: [{type: 'average', name: 'Average'}]
                            }
                        }
                    ]
                };
            
            clothing_consumption_options = {

                    // Setup grid
                    grid: {
                        x: 40,
                        x2: 40,
                        y: 35,
                        y2: 25
                    },

                    // Add tooltip
                    tooltip: {
                        trigger: 'axis'
                    },

                    // Add legend
                    legend: {
                    	data: ['2016MADCon搜索分享会-深圳站', '创世纪—“投”脑风暴—深圳站【聚募联盟全国巡讲No.2】']
                    },

                    // Enable drag recalculate
                    calculable: true,

                    // Horizontal axis
                    xAxis: [{
                        type: 'category',
                        data: ['MUJI', 'hotwind', 'Charles&Keith', 'H&M', '迪诗芬', 'Jack&Jones', 'spur韩国手工鞋', 'Mango', 'LV', '5cm', '曼古银', '索菲娅SAFIYA']
                    }],

                    // Vertical axis
                    yAxis: [{
                        type: 'value'
                    }],

                    // Add series
                    series: [
                        {
                        	name: '2016MADCon搜索分享会-深圳站',
                            type: 'bar',
                            data: [1622, 1355, 1422,1836, 1800, 800, 1685, 1500, 2500, 800, 1888, 1444],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true,
                                        textStyle: {
                                            fontWeight: 500
                                        }
                                    },
                                    color: "rgb(255, 255, 86)"
                                }
                            },
                            markLine: {
                                data: [{type: 'average', name: 'Average'}]
                            }
                        },
                        {
                        	name: '创世纪—“投”脑风暴—深圳站【聚募联盟全国巡讲No.2】',
                            type: 'bar',
                            data: [1166, 1188, 1300,1200, 2010, 600, 1800, 1000, 2120, 768, 1643, 1220],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true,
                                        textStyle: {
                                            fontWeight: 500
                                        }
                                    },
                                    color: "rgb(0, 255, 0)"
                                }
                            },
                            markLine: {
                                data: [{type: 'average', name: 'Average'}]
                            }
                        }
                    ]
                };
            
            clothing_sales_options = {

                    // Setup grid
                    grid: {
                        x: 40,
                        x2: 40,
                        y: 35,
                        y2: 25
                    },

                    // Add tooltip
                    tooltip: {
                        trigger: 'axis'
                    },

                    // Add legend
                    legend: {
                    	data: ['2016MADCon搜索分享会-深圳站', '创世纪—“投”脑风暴—深圳站【聚募联盟全国巡讲No.2】']
                    },

                    // Enable drag recalculate
                    calculable: true,

                    // Horizontal axis
                    xAxis: [{
                        type: 'category',
                        data: ['MUJI', 'hotwind', 'Charles&Keith', 'H&M', '迪诗芬', 'Jack&Jones', 'spur韩国手工鞋', 'Mango', 'LV', '5cm', '曼古银', '索菲娅SAFIYA']
                    }],

                    // Vertical axis
                    yAxis: [{
                        type: 'value'
                    }],

                    // Add series
                    series: [
                        {
                        	name: '2016MADCon搜索分享会-深圳站',
                            type: 'bar',
                            data: [1622, 1355, 1422,1836, 1800, 800, 1685, 1500, 2500, 800, 1888, 1444],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true,
                                        textStyle: {
                                            fontWeight: 500
                                        }
                                    },
                                    color: "rgb(0, 255, 255)"
                                }
                            },
                            markLine: {
                                data: [{type: 'average', name: 'Average'}]
                            }
                        },
                        {
                        	name: '创世纪—“投”脑风暴—深圳站【聚募联盟全国巡讲No.2】',
                            type: 'bar',
                            data: [1166, 1188, 1300,1200, 2010, 600, 1800, 1000, 2120, 768, 1643, 1220],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true,
                                        textStyle: {
                                            fontWeight: 500
                                        }
                                    },
                                    color: "rgb(214, 29, 214)"
                                }
                            },
                            markLine: {
                                data: [{type: 'average', name: 'Average'}]
                            }
                        }
                    ]
                };
            

            // Apply options
            // ------------------------------

            basic_columns.setOption(basic_columns_options);
            basic_columns1.setOption(basic_columns_options1);
            food_consumption.setOption(food_consumption_options);
            clothing_consumption.setOption(clothing_consumption_options);
            food_sales.setOption(food_sales_options);
            clothing_sales.setOption(clothing_sales_options);



            // Resize charts
            // ------------------------------

            window.onresize = function () {
                setTimeout(function () {
                    basic_columns.resize();
                    basic_columns1.resize();
                    food_consumption.resize();
                    clothing_consumption.resize();
                    food_sales.resize();
                    clothing_sales.resize();
                }, 200);
            }
        }
    );
});
