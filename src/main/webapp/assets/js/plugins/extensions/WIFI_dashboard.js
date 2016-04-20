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

    // Chart setup
    function sparkline(element, chartType, qty, height, interpolation, duration, interval, color) {

        // Basic setup
        // ------------------------------

        // Define main variables
        var d3Container = d3.select(element),
            margin = {top: 0, right: 0, bottom: 0, left: 0},
            width = d3Container.node().getBoundingClientRect().width - margin.left - margin.right,
            height = height - margin.top - margin.bottom;


        // Generate random data (for demo only)
        var data = [];
        for (var i=0; i < qty; i++) {
            data.push(Math.floor(Math.random() * qty) + 5)
        }



        // Construct scales
        // ------------------------------

        // Horizontal
        var x = d3.scale.linear().range([0, width]);

        // Vertical
        var y = d3.scale.linear().range([height - 5, 5]);



        // Set input domains
        // ------------------------------

        // Horizontal
        x.domain([1, qty - 3])

        // Vertical
        y.domain([0, qty])
            


        // Construct chart layout
        // ------------------------------

        // Line
        var line = d3.svg.line()
            .interpolate(interpolation)
            .x(function(d, i) { return x(i); })
            .y(function(d, i) { return y(d); });

        // Area
        var area = d3.svg.area()
            .interpolate(interpolation)
            .x(function(d,i) { 
                return x(i); 
            })
            .y0(height)
            .y1(function(d) { 
                return y(d); 
            });



        // Create SVG
        // ------------------------------

        // Container
        var container = d3Container.append('svg');

        // SVG element
        var svg = container
            .attr('width', width + margin.left + margin.right)
            .attr('height', height + margin.top + margin.bottom)
            .append("g")
                .attr("transform", "translate(" + margin.left + "," + margin.top + ")");



        // Add mask for animation
        // ------------------------------

        // Add clip path
        var clip = svg.append("defs")
            .append("clipPath")
            .attr('id', function(d, i) { return "load-clip-" + element.substring(1) })

        // Add clip shape
        var clips = clip.append("rect")
            .attr('class', 'load-clip')
            .attr("width", 0)
            .attr("height", height);

        // Animate mask
        clips
            .transition()
                .duration(1000)
                .ease('linear')
                .attr("width", width);



        //
        // Append chart elements
        //

        // Main path
        var path = svg.append("g")
            .attr("clip-path", function(d, i) { return "url(#load-clip-" + element.substring(1) + ")"})
            .append("path")
                .datum(data)
                .attr("transform", "translate(" + x(0) + ",0)");

        // Add path based on chart type
        if(chartType == "area") {
            path.attr("d", area).attr('class', 'd3-area').style("fill", color); // area
        }
        else {
            path.attr("d", line).attr("class", "d3-line d3-line-medium").style('stroke', color); // line
        }

        // Animate path
        path
            .style('opacity', 0)
            .transition()
                .duration(750)
                .style('opacity', 1);



        // Set update interval. For demo only
        // ------------------------------

        setInterval(function() {

            // push a new data point onto the back
            data.push(Math.floor(Math.random() * qty) + 5);

            // pop the old data point off the front
            data.shift();

            update();

        }, interval);



        // Update random data. For demo only
        // ------------------------------

        function update() {

            // Redraw the path and slide it to the left
            path
                .attr("transform", null)
                .transition()
                    .duration(duration)
                    .ease("linear")
                    .attr("transform", "translate(" + x(0) + ",0)");

            // Update path type
            if(chartType == "area") {
                path.attr("d", area).attr('class', 'd3-area').style("fill", color)
            }
            else {
                path.attr("d", line).attr("class", "d3-line d3-line-medium").style('stroke', color);
            }
        }



        // Resize chart
        // ------------------------------

        // Call function on window resize
        $(window).on('resize', resizeSparklines);

        // Call function on sidebar width change
        $('.sidebar-control').on('click', resizeSparklines);

        // Resize function
        // 
        // Since D3 doesn't support SVG resize by default,
        // we need to manually specify parts of the graph that need to 
        // be updated on window resize
        function resizeSparklines() {

            // Layout variables
            width = d3Container.node().getBoundingClientRect().width - margin.left - margin.right;


            // Layout
            // -------------------------

            // Main svg width
            container.attr("width", width + margin.left + margin.right);

            // Width of appended group
            svg.attr("width", width + margin.left + margin.right);

            // Horizontal range
            x.range([0, width]);


            // Chart elements
            // -------------------------

            // Clip mask
            clips.attr("width", width);

            // Line
            svg.select(".d3-line").attr("d", line);

            // Area
            svg.select(".d3-area").attr("d", area);
        }
    }
    
    // Chart setup
    function campaignDonut(element, size) {


        // Basic setup
        // ------------------------------

        // Add data set
        var data = [
            {
                "browser": "A区域",
                "icon": "<i class='icon-cutlery position-left'></i>",
                "value": 167,
                "color" : "#66BB6A"
            }, {
                "browser": "B区域",
                "icon": "<i class='icon-street-view position-left'></i>",
                "value": 248,
                "color": "#9575CD"
            }, {
                "browser":"C区域",
                "icon": "<i class='icon-diamond position-left'></i>",
                "value": 309,
                "color": "#FF7043"
            }
        ];

        // Main variables
        var d3Container = d3.select(element),
            distance = 2, // reserve 2px space for mouseover arc moving
            radius = (size/2) - distance,
            sum = d3.sum(data, function(d) { return d.value; })



        // Tooltip
        // ------------------------------

        var tip = d3.tip()
            .attr('class', 'd3-tip')
            .offset([-10, 0])
            .direction('e')
            .html(function (d) {
                return "<ul class='list-unstyled mb-5'>" +
                    "<li>" + "<div class='text-size-base mb-5 mt-5'>" + d.data.icon + d.data.browser + "</div>" + "</li>" +
                    "<li>" + "参与人数: &nbsp;" + "<span class='text-semibold pull-right'>" + d.value + "</span>" + "</li>" +
                    "<li>" + "占比: &nbsp;" + "<span class='text-semibold pull-right'>" + (100 / (sum / d.value)).toFixed(2) + "%" + "</span>" + "</li>" +
                "</ul>";
            })



        // Create chart
        // ------------------------------

        // Add svg element
        var container = d3Container.append("svg").call(tip);
        
        // Add SVG group
        var svg = container
            .attr("width", size)
            .attr("height", size)
            .append("g")
                .attr("transform", "translate(" + (size / 2) + "," + (size / 2) + ")");  



        // Construct chart layout
        // ------------------------------

        // Pie
        var pie = d3.layout.pie()
            .sort(null)
            .startAngle(Math.PI)
            .endAngle(3 * Math.PI)
            .value(function (d) { 
                return d.value;
            }); 

        // Arc
        var arc = d3.svg.arc()
            .outerRadius(radius)
            .innerRadius(radius / 2);



        //
        // Append chart elements
        //

        // Group chart elements
        var arcGroup = svg.selectAll(".d3-arc")
            .data(pie(data))
            .enter()
            .append("g") 
                .attr("class", "d3-arc")
                .style('stroke', '#fff')
                .style('cursor', 'pointer');
        
        // Append path
        var arcPath = arcGroup
            .append("path")
            .style("fill", function (d) { return d.data.color; });

        // Add tooltip
        arcPath
            .on('mouseover', function (d, i) {

                // Transition on mouseover
                d3.select(this)
                .transition()
                    .duration(500)
                    .ease('elastic')
                    .attr('transform', function (d) {
                        d.midAngle = ((d.endAngle - d.startAngle) / 2) + d.startAngle;
                        var x = Math.sin(d.midAngle) * distance;
                        var y = -Math.cos(d.midAngle) * distance;
                        return 'translate(' + x + ',' + y + ')';
                    });
            })

            .on("mousemove", function (d) {
                
                // Show tooltip on mousemove
                tip.show(d)
                    .style("top", (d3.event.pageY - 40) + "px")
                    .style("left", (d3.event.pageX + 30) + "px");
            })

            .on('mouseout', function (d, i) {

                // Mouseout transition
                d3.select(this)
                .transition()
                    .duration(500)
                    .ease('bounce')
                    .attr('transform', 'translate(0,0)');

                // Hide tooltip
                tip.hide(d);
            });

        // Animate chart on load
        arcPath
            .transition()
                .delay(function(d, i) { return i * 500; })
                .duration(500)
                .attrTween("d", function(d) {
                    var interpolate = d3.interpolate(d.startAngle,d.endAngle);
                    return function(t) {
                        d.endAngle = interpolate(t);
                        return arc(d);  
                    }; 
                });
    }
    
    // Chart setup
    function campaignStatusPie(element, size) {


        // Basic setup
        // ------------------------------

        // Add data set
        var data = [
            {
                "status": "5~20岁",
                "icon": "<span class='status-mark border-blue-300 position-left'></span>",
                "value": 439,
                "color": "#29B6F6"
            }, {
                "status": "20~30岁",
                "icon": "<span class='status-mark border-danger-300 position-left'></span>",
                "value": 290,
                "color": "#EF5350"
            }, {
                "status": "30~40岁",
                "icon": "<span class='status-mark border-success-300 position-left'></span>",
                "value": 190,
                "color": "#81C784"
            }, {
                "status": "40岁以上",
                "icon": "<span class='status-mark border-grey-300 position-left'></span>",
                "value": 148,
                "color": "#999"
            }
        ];

        // Main variables
        var d3Container = d3.select(element),
            distance = 2, // reserve 2px space for mouseover arc moving
            radius = (size/2) - distance,
            sum = d3.sum(data, function(d) { return d.value; })



        // Tooltip
        // ------------------------------

        var tip = d3.tip()
            .attr('class', 'd3-tip')
            .offset([-10, 0])
            .direction('e')
            .html(function (d) {
                return "<ul class='list-unstyled mb-5'>" +
                    "<li>" + "<div class='text-size-base mb-5 mt-5'>" + d.data.icon + d.data.status + "</div>" + "</li>" +
                    "<li>" + "总数: &nbsp;" + "<span class='text-semibold pull-right'>" + d.value + "</span>" + "</li>" +
                    "<li>" + "占比: &nbsp;" + "<span class='text-semibold pull-right'>" + (100 / (sum / d.value)).toFixed(2) + "%" + "</span>" + "</li>" +
                "</ul>";
            })



        // Create chart
        // ------------------------------

        // Add svg element
        var container = d3Container.append("svg").call(tip);
        
        // Add SVG group
        var svg = container
            .attr("width", size)
            .attr("height", size)
            .append("g")
                .attr("transform", "translate(" + (size / 2) + "," + (size / 2) + ")");  



        // Construct chart layout
        // ------------------------------

        // Pie
        var pie = d3.layout.pie()
            .sort(null)
            .startAngle(Math.PI)
            .endAngle(3 * Math.PI)
            .value(function (d) { 
                return d.value;
            }); 

        // Arc
        var arc = d3.svg.arc()
            .outerRadius(radius)
            .innerRadius(radius / 2);



        //
        // Append chart elements
        //

        // Group chart elements
        var arcGroup = svg.selectAll(".d3-arc")
            .data(pie(data))
            .enter()
            .append("g") 
                .attr("class", "d3-arc")
                .style('stroke', '#fff')
                .style('cursor', 'pointer');
        
        // Append path
        var arcPath = arcGroup
            .append("path")
            .style("fill", function (d) { return d.data.color; });

        // Add tooltip
        arcPath
            .on('mouseover', function (d, i) {

                // Transition on mouseover
                d3.select(this)
                .transition()
                    .duration(500)
                    .ease('elastic')
                    .attr('transform', function (d) {
                        d.midAngle = ((d.endAngle - d.startAngle) / 2) + d.startAngle;
                        var x = Math.sin(d.midAngle) * distance;
                        var y = -Math.cos(d.midAngle) * distance;
                        return 'translate(' + x + ',' + y + ')';
                    });
            })

            .on("mousemove", function (d) {
                
                // Show tooltip on mousemove
                tip.show(d)
                    .style("top", (d3.event.pageY - 40) + "px")
                    .style("left", (d3.event.pageX + 30) + "px");
            })

            .on('mouseout', function (d, i) {

                // Mouseout transition
                d3.select(this)
                .transition()
                    .duration(500)
                    .ease('bounce')
                    .attr('transform', 'translate(0,0)');

                // Hide tooltip
                tip.hide(d);
            });

        // Animate chart on load
        arcPath
            .transition()
                .delay(function(d, i) { return i * 500; })
                .duration(500)
                .attrTween("d", function(d) {
                    var interpolate = d3.interpolate(d.startAngle,d.endAngle);
                    return function(t) {
                        d.endAngle = interpolate(t);
                        return arc(d);  
                    }; 
                });
    }
    
    function generateBarChart(element, barQty, height, animate, easing, duration, delay, color, tooltip) {

        // Basic setup
        // ------------------------------

        // Add data set
        var bardata = [];
        for (var i=0; i < barQty; i++) {
            bardata.push(Math.round(Math.random()*10) + 10)
        }

        // Main variables
        var d3Container = d3.select(element),
            width = d3Container.node().getBoundingClientRect().width;
        


        // Construct scales
        // ------------------------------

        // Horizontal
        var x = d3.scale.ordinal()
            .rangeBands([0, width], 0.3)

        // Vertical
        var y = d3.scale.linear()
            .range([0, height]);



        // Set input domains
        // ------------------------------

        // Horizontal
        x.domain(d3.range(0, bardata.length))

        // Vertical
        y.domain([0, d3.max(bardata)])



        // Create chart
        // ------------------------------

        // Add svg element
        var container = d3Container.append('svg');

        // Add SVG group
        var svg = container
            .attr('width', width)
            .attr('height', height)
            .append('g');



        //
        // Append chart elements
        //

        // Bars
        var bars = svg.selectAll('rect')
            .data(bardata)
            .enter()
            .append('rect')
                .attr('class', 'd3-random-bars')
                .attr('width', x.rangeBand())
                .attr('x', function(d,i) {
                    return x(i);
                })
                .style('fill', color);



        // Tooltip
        // ------------------------------

        var tip = d3.tip()
            .attr('class', 'd3-tip')
            .offset([-10, 0]);

        // Show and hide
        if(tooltip == "hours" || tooltip == "goal" || tooltip == "members") {
            bars.call(tip)
                .on('mouseover', tip.show)
                .on('mouseout', tip.hide);
        }

        // Daily meetings tooltip content
        if(tooltip == "hours") {
            tip.html(function (d, i) {
                return "<div class='text-center'>" +
                        "<h6 class='no-margin'>" + d + "</h6>" +
                        "<span class='text-size-small'>meetings</span>" +
                        "<div class='text-size-small'>" + i + ":00" + "</div>" +
                    "</div>"
            });
        }

        // Statements tooltip content
        if(tooltip == "goal") {
            tip.html(function (d, i) {
                return "<div class='text-center'>" +
                        "<h6 class='no-margin'>" + d + "</h6>" +
                        "<span class='text-size-small'>statements</span>" +
                        "<div class='text-size-small'>" + i + ":00" + "</div>" +
                    "</div>"
            });
        }

        // Online members tooltip content
        if(tooltip == "members") {
            tip.html(function (d, i) {
                return "<div class='text-center'>" +
                        "<h6 class='no-margin'>" + d + "0" + "(人)</h6>" +
                        "<span class='text-size-small'></span>" +
                        "<div class='text-size-small'>" + i + ":00" + "</div>" +
                    "</div>"
            });
        }



        // Bar loading animation
        // ------------------------------

        // Choose between animated or static
        if(animate) {
            withAnimation();
        } else {
            withoutAnimation();
        }

        // Animate on load
        function withAnimation() {
            bars
                .attr('height', 0)
                .attr('y', height)
                .transition()
                    .attr('height', function(d) {
                        return y(d);
                    })
                    .attr('y', function(d) {
                        return height - y(d);
                    })
                    .delay(function(d, i) {
                        return i * delay;
                    })
                    .duration(duration)
                    .ease(easing);
        }

        // Load without animateion
        function withoutAnimation() {
            bars
                .attr('height', function(d) {
                    return y(d);
                })
                .attr('y', function(d) {
                    return height - y(d);
                })
        }



        // Resize chart
        // ------------------------------

        // Call function on window resize
        $(window).on('resize', barsResize);

        // Call function on sidebar width change
        $('.sidebar-control').on('click', barsResize);

        // Resize function
        // 
        // Since D3 doesn't support SVG resize by default,
        // we need to manually specify parts of the graph that need to 
        // be updated on window resize
        function barsResize() {

            // Layout variables
            width = d3Container.node().getBoundingClientRect().width;


            // Layout
            // -------------------------

            // Main svg width
            container.attr("width", width);

            // Width of appended group
            svg.attr("width", width);

            // Horizontal range
            x.rangeBands([0, width], 0.3);


            // Chart elements
            // -------------------------

            // Bars
            svg.selectAll('.d3-random-bars')
                .attr('width', x.rangeBand())
                .attr('x', function(d,i) {
                    return x(i);
                });
        }
    }
    
    // Chart setup
    function dailyRevenue(element, height) {


        // Basic setup
        // ------------------------------

        // Add data set
        var dataset = [
            {
                "date": "04/13/14",
                "alpha": "60"
            }, {
                "date": "04/14/14",
                "alpha": "35"
            }, {
                "date": "04/15/14",
                "alpha": "65"
            }, {
                "date": "04/16/14",
                "alpha": "50"
            }, {
                "date": "04/17/14",
                "alpha": "65"
            }, {
                "date": "04/18/14",
                "alpha": "20"
            }, {
                "date": "04/19/14",
                "alpha": "60"
            }
        ];

        // Main variables
        var d3Container = d3.select(element),
            margin = {top: 0, right: 0, bottom: 0, left: 0},
            width = d3Container.node().getBoundingClientRect().width - margin.left - margin.right,
            height = height - margin.top - margin.bottom,
            padding = 20;

        // Format date
        var parseDate = d3.time.format("%m/%d/%y").parse,
            formatDate = d3.time.format("%a, %B %e");



        // Add tooltip
        // ------------------------------

        var tooltip = d3.tip()
            .attr('class', 'd3-tip')
            .html(function (d) {
                return "<ul class='list-unstyled mb-5'>" +
                    "<li>" + "<div class='text-size-base mt-5 mb-5'><i class='icon-check2 position-left'></i>" + formatDate(d.date) + "</div>" + "</li>" +
                    "<li>" + "使用人数: &nbsp;" + "<span class='text-semibold pull-right'>" + d.alpha + "</span>" + "</li>" +
                    "<li>" + "当日收益: &nbsp; " + "<span class='text-semibold pull-right'>" + "¥" + (d.alpha * 25).toFixed(2) + "</span>" + "</li>" + 
                "</ul>";
            });



        // Create chart
        // ------------------------------

        // Add svg element
        var container = d3Container.append('svg');

        // Add SVG group
        var svg = container
                .attr('width', width + margin.left + margin.right)
                .attr('height', height + margin.top + margin.bottom)
                .append("g")
                    .attr("transform", "translate(" + margin.left + "," + margin.top + ")")
                    .call(tooltip);



        // Load data
        // ------------------------------

        dataset.forEach(function (d) {
            d.date = parseDate(d.date);
            d.alpha = +d.alpha;
        });



        // Construct scales
        // ------------------------------

        // Horizontal
        var x = d3.time.scale()
            .range([padding, width - padding]);

        // Vertical
        var y = d3.scale.linear()
            .range([height, 5]);



        // Set input domains
        // ------------------------------

        // Horizontal
        x.domain(d3.extent(dataset, function (d) {
            return d.date;
        }));

        // Vertical
        y.domain([0, d3.max(dataset, function (d) {
            return Math.max(d.alpha);
        })]);



        // Construct chart layout
        // ------------------------------

        // Line
        var line = d3.svg.line()
            .x(function(d) {
                return x(d.date);
            })
            .y(function(d) {
                return y(d.alpha)
            });



        //
        // Append chart elements
        //

        // Add mask for animation
        // ------------------------------

        // Add clip path
        var clip = svg.append("defs")
            .append("clipPath")
            .attr("id", "clip-line-small");

        // Add clip shape
        var clipRect = clip.append("rect")
            .attr('class', 'clip')
            .attr("width", 0)
            .attr("height", height);

        // Animate mask
        clipRect
              .transition()
                  .duration(1000)
                  .ease('linear')
                  .attr("width", width);



        // Line
        // ------------------------------

        // Path
        var path = svg.append('path')
            .attr({
                'd': line(dataset),
                "clip-path": "url(#clip-line-small)",
                'class': 'd3-line d3-line-medium'
            })
            .style('stroke', '#fff');

        // Animate path
        svg.select('.line-tickets')
            .transition()
                .duration(1000)
                .ease('linear');



        // Add vertical guide lines
        // ------------------------------

        // Bind data
        var guide = svg.append('g')
            .selectAll('.d3-line-guides-group')
            .data(dataset);

        // Append lines
        guide
            .enter()
            .append('line')
                .attr('class', 'd3-line-guides')
                .attr('x1', function (d, i) {
                    return x(d.date);
                })
                .attr('y1', function (d, i) {
                    return height;
                })
                .attr('x2', function (d, i) {
                    return x(d.date);
                })
                .attr('y2', function (d, i) {
                    return height;
                })
                .style('stroke', 'rgba(255,255,255,0.3)')
                .style('stroke-dasharray', '4,2')
                .style('shape-rendering', 'crispEdges');

        // Animate guide lines
        guide
            .transition()
                .duration(1000)
                .delay(function(d, i) { return i * 150; })
                .attr('y2', function (d, i) {
                    return y(d.alpha);
                });



        // Alpha app points
        // ------------------------------

        // Add points
        var points = svg.insert('g')
            .selectAll('.d3-line-circle')
            .data(dataset)
            .enter()
            .append('circle')
                .attr('class', 'd3-line-circle d3-line-circle-medium')
                .attr("cx", line.x())
                .attr("cy", line.y())
                .attr("r", 3)
                .style('stroke', '#fff')
                .style('fill', '#29B6F6');



        // Animate points on page load
        points
            .style('opacity', 0)
            .transition()
                .duration(250)
                .ease('linear')
                .delay(1000)
                .style('opacity', 1);


        // Add user interaction
        points
            .on("mouseover", function (d) {
                tooltip.offset([-10, 0]).show(d);

                // Animate circle radius
                d3.select(this).transition().duration(250).attr('r', 4);
            })

            // Hide tooltip
            .on("mouseout", function (d) {
                tooltip.hide(d);

                // Animate circle radius
                d3.select(this).transition().duration(250).attr('r', 3);
            });

        // Change tooltip direction of first point
        d3.select(points[0][0])
            .on("mouseover", function (d) {
                tooltip.offset([0, 10]).direction('e').show(d);

                // Animate circle radius
                d3.select(this).transition().duration(250).attr('r', 4);
            })
            .on("mouseout", function (d) {
                tooltip.direction('n').hide(d);

                // Animate circle radius
                d3.select(this).transition().duration(250).attr('r', 3);
            });

        // Change tooltip direction of last point
        d3.select(points[0][points.size() - 1])
            .on("mouseover", function (d) {
                tooltip.offset([0, -10]).direction('w').show(d);

                // Animate circle radius
                d3.select(this).transition().duration(250).attr('r', 4);
            })
            .on("mouseout", function (d) {
                tooltip.direction('n').hide(d);

                // Animate circle radius
                d3.select(this).transition().duration(250).attr('r', 3);
            })



        // Resize chart
        // ------------------------------

        // Call function on window resize
        $(window).on('resize', revenueResize);

        // Call function on sidebar width change
        $('.sidebar-control').on('click', revenueResize);

        // Resize function
        // 
        // Since D3 doesn't support SVG resize by default,
        // we need to manually specify parts of the graph that need to 
        // be updated on window resize
        function revenueResize() {

            // Layout variables
            width = d3Container.node().getBoundingClientRect().width - margin.left - margin.right;


            // Layout
            // -------------------------

            // Main svg width
            container.attr("width", width + margin.left + margin.right);

            // Width of appended group
            svg.attr("width", width + margin.left + margin.right);

            // Horizontal range
            x.range([padding, width - padding]);


            // Chart elements
            // -------------------------

            // Mask
            clipRect.attr("width", width);

            // Line path
            svg.selectAll('.d3-line').attr("d", line(dataset));

            // Circles
            svg.selectAll('.d3-line-circle').attr("cx", line.x());

            // Guide lines
            svg.selectAll('.d3-line-guides')
                .attr('x1', function (d, i) {
                    return x(d.date);
                })
                .attr('x2', function (d, i) {
                    return x(d.date);
                });
        }
    }
