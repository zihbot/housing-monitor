import React from 'react';
import { HouseData } from './HouseData';
import { HousesList } from './HousesList';

type State = {
    selectedHouse?: bigint
}

class Listings extends React.Component<{}, State> {
    constructor(props: {}) {
        super(props);

        this.state = {
            selectedHouse: undefined
        };
    }

    render() {
        return (
            <>
                { this.state.selectedHouse 
                    ? <HouseData 
                        id={this.state.selectedHouse} 
                        onBack={() => this.setState({selectedHouse: undefined})}/> 
                    : <HousesList 
                        onSelectHouse={ n => this.setState({selectedHouse: n}) } /> }
            </>
        )
    }
}

export default Listings