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


    // Switchery toggles
    // ------------------------------

    var switches = Array.prototype.slice.call(document.querySelectorAll('.switch'));
    switches.forEach(function(html) {
        var switchery = new Switchery(html, {color: '#4CAF50'});
    });

    // Traffic sources stream chart
    // ------------------------------

    trafficSources('#traffic-sources', 330); // initialize chart

    // Chart setup
    function trafficSources(element, height) {


        // Basic setup
        // ------------------------------

        // Define main variables
        var d3Container = d3.select(element),
            margin = {top: 5, right: 50, bottom: 40, left: 50},
            width = d3Container.node().getBoundingClientRect().width - margin.left - margin.right,
            height = height - margin.top - margin.bottom,
            tooltipOffset = 30;

        // Tooltip
        var tooltip = d3Container
            .append("div")
            .attr("class", "d3-tip e")
            .style("display", "none")

        // Format date
        var format = d3.time.format("%m/%d/%y %H:%M");
        var formatDate = d3.time.format("%H:%M");

        // Colors
        var colorrange = ['#03A9F4', '#29B6F6', '#4FC3F7', '#81D4FA', '#B3E5FC', '#E1F5FE'];



        // Construct scales
        // ------------------------------

        // Horizontal
        var x = d3.time.scale().range([0, width]);

        // Vertical
        var y = d3.scale.linear().range([height, 0]);

        // Colors
        var z = d3.scale.ordinal().range(colorrange);



        // Create axes
        // ------------------------------

        // Horizontal
        var xAxis = d3.svg.axis()
            .scale(x)
            .orient("bottom")
            .ticks(d3.time.hours, 4)
            .innerTickSize(4)
            .tickPadding(8)
            .tickFormat(d3.time.format("%H:%M")); // Display hours and minutes in 24h format

        // Left vertical
        var yAxis = d3.svg.axis()
            .scale(y)
            .ticks(6)
            .innerTickSize(4)
            .outerTickSize(0)
            .tickPadding(8)
            .tickFormat(function (d) { return (d/1000) + "k"; });

        // Right vertical
        var yAxis2 = yAxis;

        // Dash lines
        var gridAxis = d3.svg.axis()
            .scale(y)
            .orient("left")
            .ticks(6)
            .tickPadding(8)
            .tickFormat("")
            .tickSize(-width, 0, 0);



        // Create chart
        // ------------------------------

        // Container
        var container = d3Container.append("svg")

        // SVG element
        var svg = container
            .attr('width', width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
                .append("g")
                .attr("transform", "translate(" + margin.left + "," + margin.top + ")");



        // Construct chart layout
        // ------------------------------

        // Stack
        var stack = d3.layout.stack()
            .offset("silhouette")
            .values(function(d) { return d.values; })
            .x(function(d) { return d.date; })
            .y(function(d) { return d.value; });

        // Nest
        var nest = d3.nest()
            .key(function(d) { return d.key; });

        // Area
        var area = d3.svg.area()
            .interpolate("cardinal")
            .x(function(d) { return x(d.date); })
            .y0(function(d) { return y(d.y0); })
            .y1(function(d) { return y(d.y0 + d.y); });



        // Load data
        // ------------------------------
        d3.csv("assets/demo_data/dashboard/traffic_sources.csv", function (error, data) {
            // Pull out values
            data.forEach(function (d) {
                d.date = format.parse(d.date);
                d.value = +d.value;
            });

            // Stack and nest layers
            var layers = stack(nest.entries(data));



            // Set input domains
            // ------------------------------

            // Horizontal
            x.domain(d3.extent(data, function(d, i) { return d.date; }));

            // Vertical
            y.domain([0, d3.max(data, function(d) { return d.y0 + d.y; })]);



            // Add grid
            // ------------------------------

            // Horizontal grid. Must be before the group
            svg.append("g")
                .attr("class", "d3-grid-dashed")
                .call(gridAxis);



            //
            // Append chart elements
            //

            // Stream layers
            // ------------------------------

            // Create group
            var group = svg.append('g')
                .attr('class', 'streamgraph-layers-group');

            // And append paths to this group
            var layer = group.selectAll(".streamgraph-layer")
                .data(layers)
                .enter()
                    .append("path")
                    .attr("class", "streamgraph-layer")
                    .attr("d", function(d) { return area(d.values); })                    
                    .style('stroke', '#fff')
                    .style('stroke-width', 0.5)
                    .style("fill", function(d, i) { return z(i); });

            // Add transition
            var layerTransition = layer
                .style('opacity', 0)
                .transition()
                    .duration(750)
                    .delay(function(d, i) { return i * 50; })
                    .style('opacity', 1)



            // Append axes
            // ------------------------------

            //
            // Left vertical
            //

            svg.append("g")
                .attr("class", "d3-axis d3-axis-left d3-axis-solid")
                .call(yAxis.orient("left"));

            // Hide first tick
            d3.select(svg.selectAll('.d3-axis-left .tick text')[0][0])
                .style("visibility", "hidden");


            //
            // Right vertical
            //

            svg.append("g")
                .attr("class", "d3-axis d3-axis-right d3-axis-solid")
                .attr("transform", "translate(" + width + ", 0)")
                .call(yAxis2.orient("right"));

            // Hide first tick
            d3.select(svg.selectAll('.d3-axis-right .tick text')[0][0])
                .style("visibility", "hidden");


            //
            // Horizontal
            //

            var xaxisg = svg.append("g")
                .attr("class", "d3-axis d3-axis-horizontal d3-axis-solid")
                .attr("transform", "translate(0," + height + ")")
                .call(xAxis);

            // Add extra subticks for hidden hours
            xaxisg.selectAll(".d3-axis-subticks")
                .data(x.ticks(d3.time.hours), function(d) { return d; })
                .enter()
                .append("line")
                .attr("class", "d3-axis-subticks")
                .attr("y1", 0)
                .attr("y2", 4)
                .attr("x1", x)
                .attr("x2", x);



            // Add hover line and pointer
            // ------------------------------

            // Append group to the group of paths to prevent appearance outside chart area
            var hoverLineGroup = group.append("g")
                .attr("class", "hover-line");

            // Add line
            var hoverLine = hoverLineGroup
                .append("line")
                .attr("y1", 0)
                .attr("y2", height)
                .style('fill', 'none')
                .style('stroke', '#fff')
                .style('stroke-width', 1)
                .style('pointer-events', 'none')
                .style('shape-rendering', 'crispEdges')
                .style("opacity", 0);

            // Add pointer
            var hoverPointer = hoverLineGroup
                .append("rect")
                .attr("x", 2)
                .attr("y", 2)
                .attr("width", 6)
                .attr("height", 6)
                .style('fill', '#03A9F4')
                .style('stroke', '#fff')
                .style('stroke-width', 1)
                .style('shape-rendering', 'crispEdges')
                .style('pointer-events', 'none')
                .style("opacity", 0);



            // Append events to the layers group
            // ------------------------------

            layerTransition.each("end", function() {
                layer
                    .on("mouseover", function (d, i) {
                        svg.selectAll(".streamgraph-layer")
                            .transition()
                            .duration(250)
                            .style("opacity", function (d, j) {
                                return j != i ? 0.75 : 1; // Mute all except hovered
                            });
                    })

                    .on("mousemove", function (d, i) {
                        mouse = d3.mouse(this);
                        mousex = mouse[0];
                        mousey = mouse[1];
                        datearray = [];
                        var invertedx = x.invert(mousex);
                        invertedx = invertedx.getHours();
                        var selected = (d.values);
                        for (var k = 0; k < selected.length; k++) {
                            datearray[k] = selected[k].date
                            datearray[k] = datearray[k].getHours();
                        }
                        mousedate = datearray.indexOf(invertedx);
                        pro = d.values[mousedate].value;


                        // Display mouse pointer
                        hoverPointer
                            .attr("x", mousex - 3)
                            .attr("y", mousey - 6)
                            .style("opacity", 1);

                        hoverLine
                            .attr("x1", mousex)
                            .attr("x2", mousex)
                            .style("opacity", 1);

                        //
                        // Tooltip
                        //

                        // Tooltip data
                        tooltip.html(
                            "<ul class='list-unstyled mb-5'>" +
                                "<li>" + "<div class='text-size-base mt-5 mb-5'><i class='icon-circle-left2 position-left'></i>" + d.key + "</div>" + "</li>" +
                                "<li>" + "Visits: &nbsp;" + "<span class='text-semibold pull-right'>" + pro + "</span>" + "</li>" +
                                "<li>" + "Time: &nbsp; " + "<span class='text-semibold pull-right'>" + formatDate(d.values[mousedate].date) + "</span>" + "</li>" + 
                            "</ul>"
                        )
                        .style("display", "block");

                        // Tooltip arrow
                        tooltip.append('div').attr('class', 'd3-tip-arrow');
                    })

                    .on("mouseout", function (d, i) {

                        // Revert full opacity to all paths
                        svg.selectAll(".streamgraph-layer")
                            .transition()
                            .duration(250)
                            .style("opacity", 1);

                        // Hide cursor pointer
                        hoverPointer.style("opacity", 0);

                        // Hide tooltip
                        tooltip.style("display", "none");

                        hoverLine.style("opacity", 0);
                    });
                });



            // Append events to the chart container
            // ------------------------------

            d3Container
                .on("mousemove", function (d, i) {
                    mouse = d3.mouse(this);
                    mousex = mouse[0];
                    mousey = mouse[1];

                    // Display hover line
                        //.style("opacity", 1);


                    // Move tooltip vertically
                    tooltip.style("top", (mousey - ($('.d3-tip').outerHeight() / 2)) - 2 + "px") // Half tooltip height - half arrow width

                    // Move tooltip horizontally
                    if(mousex >= ($(element).outerWidth() - $('.d3-tip').outerWidth() - margin.right - (tooltipOffset * 2))) {
                        tooltip
                            .style("left", (mousex - $('.d3-tip').outerWidth() - tooltipOffset) + "px") // Change tooltip direction from right to left to keep it inside graph area
                            .attr("class", "d3-tip w");
                    }
                    else {
                        tooltip
                            .style("left", (mousex + tooltipOffset) + "px" )
                            .attr("class", "d3-tip e");
                    }
                });
        });



        // Resize chart
        // ------------------------------

        // Call function on window resize
        $(window).on('resize', resizeStream);

        // Call function on sidebar width change
        $('.sidebar-control').on('click', resizeStream);

        // Resize function
        // 
        // Since D3 doesn't support SVG resize by default,
        // we need to manually specify parts of the graph that need to 
        // be updated on window resize
        function resizeStream() {

            // Layout
            // -------------------------

            // Define width
            width = d3Container.node().getBoundingClientRect().width - margin.left - margin.right;

            // Main svg width
            container.attr("width", width + margin.left + margin.right);

            // Width of appended group
            svg.attr("width", width + margin.left + margin.right);

            // Horizontal range
            x.range([0, width]);


            // Chart elements
            // -------------------------

            // Horizontal axis
            svg.selectAll('.d3-axis-horizontal').call(xAxis);

            // Horizontal axis subticks
            svg.selectAll('.d3-axis-subticks').attr("x1", x).attr("x2", x);

            // Grid lines width
            svg.selectAll(".d3-grid-dashed").call(gridAxis.tickSize(-width, 0, 0))

            // Right vertical axis
            svg.selectAll(".d3-axis-right").attr("transform", "translate(" + width + ", 0)");

            // Area paths
            svg.selectAll('.streamgraph-layer').attr("d", function(d) { return area(d.values); });
        }
    }

    // Sparklines
    // ------------------------------

    // Initialize chart
    sparkline("#server-load", "area", 30, 50, "basis", 750, 2000, "rgba(255,255,255,0.5)");

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




    // Daily revenue line chart
    // ------------------------------

    dailyRevenue('#today-revenue', 50); // initialize chart

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
                    "<li>" + "Sales: &nbsp;" + "<span class='text-semibold pull-right'>" + d.alpha + "</span>" + "</li>" +
                    "<li>" + "Revenue: &nbsp; " + "<span class='text-semibold pull-right'>" + "$" + (d.alpha * 25).toFixed(2) + "</span>" + "</li>" + 
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

    // Bar charts with random data
    // ------------------------------

    // Initialize charts
    generateBarChart("#members-online", 24, 50, true, "elastic", 1200, 50, "rgba(255,255,255,0.5)", "members");

    // Chart setup
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
                        "<h6 class='no-margin'>" + d + "0" + "</h6>" +
                        "<span class='text-size-small'>members</span>" +
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


});
