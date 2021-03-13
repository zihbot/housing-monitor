import React from 'react';
import { useRestGetClient } from './HouseDataClient';
import { KeyValuePair } from './model';

type Props = {
    id: bigint,
    onBack: () => void
}

export const HouseData: React.FC<Props> = ({id, onBack}) => {
    const client = useRestGetClient<KeyValuePair[]>('properties/' + id);

    return (<div>
                { client.result.data && client.result.data.map(kv => 
                    <div className='row pb-1' key={kv.key}>
                        <div className='col-3'>{kv.key}</div>
                        <div className='col-9'>{kv.value}</div>
                    </div>
                ) }
            <button className='btn btn-secondary' onClick={onBack}>Vissza</button>
        </div>
    );
}
