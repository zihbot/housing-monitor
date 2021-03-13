import React, { useState } from 'react';
import { useRestGetClient, useRestPostClient } from './HouseDataClient';
import { HouseElement } from './model';

type Props = {
    onSelectHouse: (id: bigint) => void
}

export const HousesList: React.FC<Props> = ({onSelectHouse}) => {
    const getClient = useRestGetClient<HouseElement[]>('house');
    const postClient = useRestPostClient<{}>('house', getClient.getRequest);

    const [newUrl, setNewUrl] = useState('');

    const handleSubmit = (evt: React.FormEvent<HTMLFormElement>) => {
        evt.preventDefault();
        postClient.postRequest({'url': newUrl});
        setNewUrl('');
    }

    return (<>
        <div className='row mb-2'>
            <div className='col'>
                <div className='list-group'>
                { getClient.result.data && getClient.result.data.map(kv => 
                    <button className='list-group-item list-group-item-action' key={kv.id.toString()} 
                    onClick={() => onSelectHouse(kv.id)}>
                        {kv.id}: {kv.url}
                    </button>
                ) }
                </div>
            </div>
        </div>
        <div className='row'>
            <div className='col-12'>
                <form onSubmit={handleSubmit}>
                    <div className='form-group mb-2 row'>
                        <div className='col-9'>
                            <input className='form-control' type='text' value={newUrl} 
                            onChange={e => setNewUrl(e.target.value)} />
                        </div>
                        <div className='col-3'>
                            <button type='submit' className='btn btn-primary mb-2 col-12'>Küldés</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        </>
    );
}
