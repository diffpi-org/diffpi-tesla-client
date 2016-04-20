var TreeMenu = React.createClass({
  loadMenu: function() {
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      cache: false,
      success: function(data) {
        this.setState({
          data: data
        });
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },
  getInitialState: function() {
    return {
      data: []
    };
  },
  componentDidMount: function() {
    this.loadMenu();
  },

  render() {
    if (this.state.data == null) {
      return null;
    }
    return (
      <div className="category-content no-padding">
          <Menus data={this.state.data} invoke={this.props.invoke}/>
        </div>
    );
  },
});

var Menus = React.createClass({
  render() {
    var invoke = this.props.invoke;
    var Items = this.props.data.map((item) => {
      return (
        <Item item={item} invoke={invoke}/>
      );
    });

    return (
      <ul className="navigation navigation-main navigation-accordion">
        {Items}
      </ul>
    );
  }
});

var Items = React.createClass({
  render() {
    var Items = this.props.item.map((item) => {
      return (
        <Item item={item} invoke={this.props.invoke}/>
      );
    });

    return (
      <ul className="hidden-ul">
        {Items}
      </ul>
    );
  }
});

var Item = React.createClass({
  render() {
    var template = null;
    if (this.props.item.menu) {
      template = (
        <li>
            <a href="javascript:;" className="has-ul">
              <i className={this.props.item.icon}></i>
              <span>
                {this.props.item.name}
                <span className={!!this.props.item.errcode ? "badge bg-warning-"+this.props.item.errcode : ""}>{this.props.item.warning}</span>
              </span>
            </a>
            <Items item={this.props.item.menu} invoke={this.props.invoke}/>
        </li>
      )
    } else {
      template = (
        <li>
              <a href="javascript:;" onClick={()=> {this.props.invoke(this.props.item.url);}}>
                <i className={this.props.item.icon}></i>
                <span>
              {this.props.item.name}
              <span className={!!this.props.item.errcode ? "badge bg-warning-"+this.props.item.errcode : ""}>{this.props.item.warning}</span>
            </span>
              </a>
          </li>
      )
    }

    return template;
  }
});

ReactDOM.render(
	  React.createElement(TreeMenu, {
	    url: "iassets/data/json/menu.data.json",
	    invoke: invoke
	  }),
	  document.getElementById("TreeMenu")
	);