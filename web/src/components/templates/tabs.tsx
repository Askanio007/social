import React, {Component} from 'react';

interface TabState {
    selected:number;
}
class Tabs extends Component<any, TabState> {

    state: TabState = {
        selected: 0
    };

    handleChange = (index:any) => {
        this.setState({selected:index})
    };

    render() {
        let menus;
        if (this.props.children) {
            menus = React.Children.map(this.props.children, (elem:any,index:any) => {
                let style = index == this.state.selected ? 'nav-link active': 'nav-link';
                return <li className="nav-item" key={index} onClick={this.handleChange.bind(this, index)}><a className={style}>{elem.props.children}</a></li>
            })
        }
        return (
            <div className="col-sm-6">
                <ul className="nav nav-tabs menu-margin">
                    {menus}
                </ul>
                {React.Children.toArray(this.props.children)[this.state.selected]}
            </div>
        );
    }
}
export default Tabs;
