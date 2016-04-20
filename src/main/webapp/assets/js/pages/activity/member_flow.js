/* ------------------------------------------------------------------------------
 *
 *  # Dashboard configuration
 *
 *  Demo dashboard configuration. Contains charts and plugin inits
 *
 *  Version: 1.0
 *  Latest update: Aug 1, 2015
 *
 * ---------------------------------------------------------------------------- */

$(function() {    
	
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
            'echarts/chart/pie',
            'echarts/chart/funnel',
            'echarts/chart/bar',
            'echarts/chart/line'
        ],


        function (ec, limitless) {

            var basic_lines = ec.init(document.getElementById('basic_lines_come'), limitless);
            var basic_lines_back = ec.init(document.getElementById('basic_lines_back'), limitless);
            var basic_lines_leave = ec.init(document.getElementById('basic_lines_leave'), limitless);

            basic_lines_options = {

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
                    data: ['实时人数', '预计人数']
                },

                // Add custom colors
                color: ['#EF5350', '#66BB6A'],

                // Enable drag recalculate
                calculable: true,

                // Horizontal axis
                xAxis: [{
                    type: 'category',
                    boundaryGap: false,
                    data: ['09:00', '09:10', '09:20', '09:30', '09:40', '09:50', '10:00', '10:10', '10:20']
                }],

                // Vertical axis
                yAxis: [{
                    type: 'value',
                    axisLabel: {
                        formatter: '{value}'
                    }
                }],

                // Add series
                series: [
                    {
                        name: '实时人数',
                        type: 'line',
                        data: [5, 20, 30, 100, 200, 500, 300, 100, 10],
                        markLine: {
                            data: [{
                                type: 'average',
                                name: '平均人数'
                            }]
                        }
                    },
                    {
                        name: '预计人数',
                        type: 'line',
                        data: [0, 30, 50, 120, 250, 550, 250, 80, 20],
                        markLine: {
                            data: [{
                                type: 'average',
                                name: '平均人数'
                            }]
                        }
                    }
                ]
            };
            
            basic_lines_back_options = {

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
                    data: ['实时人数', '预计人数']
                },

                // Add custom colors
                color: ['#EF5350', '#66BB6A'],

                // Enable drag recalculate
                calculable: true,

                // Horizontal axis
                xAxis: [{
                    type: 'category',
                    boundaryGap: false,
                    data: ['11:30', '11:40', '11:50', '12:00', '12:10', '12:20', '12:30', '12:40', '12:50']
                }],

                // Vertical axis
                yAxis: [{
                    type: 'value',
                    axisLabel: {
                        formatter: '{value}'
                    }
                }],

                // Add series
                series: [
                    {
                        name: '实时人数',
                        type: 'line',
                        data: [20, 50, 100, 500, 300, 200, 100, 50, 10],
                        markLine: {
                            data: [{
                                type: 'average',
                                name: '平均人数'
                            }]
                        }
                    },
                    {
                        name: '预计人数',
                        type: 'line',
                        data: [10, 30, 200, 600, 200, 200, 100, 80, 10],
                        markLine: {
                            data: [{
                                type: 'average',
                                name: '平均人数'
                            }]
                        }
                    }
                ]
            };
            
            basic_lines_leave_options = {

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
                    data: ['人数']
                },

                // Add custom colors
                color: ['#EF5350', '#66BB6A'],

                // Enable drag recalculate
                calculable: true,

                // Horizontal axis
                xAxis: [{
                    type: 'category',
                    boundaryGap: false,
                    data: ['09:00', '09:20', '09:40', '10:00', '10:20', '10:40', '11:00', '11:20', '11:40','12:00']
                }],

                // Vertical axis
                yAxis: [{
                    type: 'value',
                    axisLabel: {
                        formatter: '{value}'
                    }
                }],

                // Add series
                series: [
                    {
                        name: '人数',
                        type: 'line',
                        data: [0, 10, 20, 80, 20, 10, 150, 20, 30, 0],
                        markLine: {
                            data: [{
                                type: 'average',
                                name: '平均人数'
                            }]
                        }
                    }
                ]
            };
            
            basic_lines.setOption(basic_lines_options);
            basic_lines_back.setOption(basic_lines_back_options);
            basic_lines_leave.setOption(basic_lines_leave_options);

            window.onresize = function () {
                setTimeout(function () {
                    basic_lines.resize();
                    basic_lines_back.resize();
                    basic_lines_leave.resize();
                }, 200);
            };
        }
    );
 // Switchery toggles
    // ------------------------------

    var switches = Array.prototype.slice.call(document.querySelectorAll('.switch'));
    switches.forEach(function(html) {
        var switchery = new Switchery(html, {color: '#4CAF50'});
    });

});
