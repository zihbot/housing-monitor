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

    return (<div>
            <ul>
            { getClient.result.data && getClient.result.data.map(kv => 
                <li key={kv.id.toString()}>
                    <a onClick={() => onSelectHouse(kv.id)}>{kv.id}: {kv.url}</a>
                </li>
            ) }
            </ul>
            <form onSubmit={handleSubmit}>
                <input type='text' value={newUrl} onChange={e => setNewUrl(e.target.value)} />
                <input type='submit' />
            </form>
        </div>
    );
}
