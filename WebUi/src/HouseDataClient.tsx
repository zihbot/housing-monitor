import { useCallback, useEffect, useState } from 'react';

type GetClient<T> = {
    result: {data: T | undefined},
    getRequest: () => void
}

export const useRestGetClient = <T extends unknown>(uri: string): GetClient<T> => {
    const [result, setResult] = useState<{data: T | undefined}>({ data: undefined });

    const getRequest = useCallback(() => {
        fetch('rest/' + uri)
            .then(r => {if (!r.ok) throw "REST error"; return r.json()})
            .then(r => setResult({ data: r }) )
            .catch(reason => setResult({ data: undefined }));
    }, []);

    useEffect(getRequest, []);

    return {result, getRequest};
};

type PostClient<T> = {
    result: {data: T | undefined},
    postRequest: (postData: object) => void
}

export const useRestPostClient = <T extends unknown>(
        uri: string, 
        finishCallback: () => void = () => {}): PostClient<T> => {
    const [result, setResult] = useState<{data: T | undefined}>({ data: undefined });

    const postRequest = useCallback((postData) => {
        fetch('rest/' + uri, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(postData)
        })
            .then(r => {if (!r.ok) throw "REST error"; return r.json()})
            .then(r => setResult({ data: r }))
            .catch(reason => setResult({ data: undefined }))
            .finally(finishCallback);
    }, []);

    return {result, postRequest};
};

type DeleteClient<T> = {
    result: {data: T | undefined},
    deleteRequest: () => void
}

export const useRestDeleteClient = <T extends unknown>(
    uri: string, 
    finishCallback: () => void = () => {}): DeleteClient<T> => {
    const [result, setResult] = useState<{data: T | undefined}>({ data: undefined });

    const deleteRequest = useCallback(() => {
        fetch('rest/' + uri, {
            method: 'DELETE'
        })
            .then(r => {if (!r.ok) throw "REST error"; return r.json()})
            .then(r => setResult({ data: r }) )
            .catch(reason => setResult({ data: undefined }))
            .finally(finishCallback);
    }, []);

    return {result, deleteRequest};
};
