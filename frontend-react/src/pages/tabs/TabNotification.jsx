import { getChannels } from '../../api/channels';
import { getUsers } from '../../api/users';
import { getCount, getNotifications, checkNotification } from '../../api/notifications';
import { booleanList, sortOrdersList } from '../../constants/commonFilters';
import { useState, useEffect } from 'react'

export function TabNotification() {

    const [user, setUser] = useState('')
    const [message, setMessage] = useState('')
    const [channel, setChannel] = useState('')
    const [seen, setSeen] = useState('')
    const [dateStart, setDateStart] = useState('')
    const [dateEnd, setDateEnd] = useState('')
    const [sortColumn, setSortColumn] = useState('dateSeen')
    const [sortOrder, setSortOrder] = useState('ASC')
    const [enabled, setEnabled] = useState('')

    const [limit, setLimit] = useState(null)
    const [offset, setOffset] = useState(null)
    const [totalItems, setTotalItems] = useState(1)

    const [userList, setUserList] = useState(null)
    const [channelList, setChannelList] = useState(null)
    const [notificationList, setNotificationList] = useState([])

    const sortColumnList = [
        {
            name: "Date",
            value: "dateSeen"
        }, {
            name: "Notification ID",
            value: "id"
        }]

    const fetchChannels = async () => {
        const { data } = await getChannels();
        setChannelList(data || []);
    }

    const fetchUsers = async () => {
        const { data } = await getUsers();
        setUserList(data || []);
    }

    const fetchNotifications = async (
        userId, channelId, seen,
        dateStart, dateEnd,
        limit, offset, sortColumn, sortOrder, message, enabled
    ) => {
        const { data } = await getNotifications(
            userId, channelId, seen,
            dateStart, dateEnd,
            limit, offset, sortColumn, sortOrder, message, enabled)

        setNotificationList(data || []);
    }

    const fetchTotalItems = async (userId, channelId, seen,
        dateStart, dateEnd, sortColumn, sortOrder, message, enabled) => {
        const { data } = await getCount(
            userId, channelId, seen,
            dateStart, dateEnd, sortColumn, sortOrder, message, enabled)

        setTotalItems(data || 0);
    }

    const validate = (notificationId, userId) => {
        const errors = [];
        if (!notificationId) {
            errors.push('There was a problem selecting the notification.');
        }
        if (!userId) {
            errors.push('There was a problem selecting the user.');
        }
        return errors;
    };

    const postCheckNotification = async (userId, notificationId) => {
        const { data } = await checkNotification(userId, notificationId);
        await fetchNotifications(user, channel, seen, dateStart, dateEnd, limit, offset, sortColumn, sortOrder, message, enabled);
        alert("The notification has been checked successfully!");
    }

    const handleSubmit = async (event, notificationId, userId) => {

        event.preventDefault();

        const errors = validate(notificationId, userId);
        if (errors.length > 0) {
            alert(`Please fix the following errors:\n${errors.join('\n')}`);
        }

        postCheckNotification(userId, notificationId);
    }

    function nextPage() {
        if (offset + limit < totalItems) {
            setOffset(prev => prev + limit);
        }
    }

    function prevPage() {
        if (offset >= limit) {
            setOffset(prev => prev - limit);
        }
    }

    // const currentPage = () => Math.floor(offset / limit) + 1;
    // const totalPages = () => Math.ceil(totalItems / limit);

    useEffect(() => {

        fetchChannels();
        fetchUsers();
        setSortOrder(sortOrdersList[0].value) // Set default sort order to the first one
        setSortColumn(sortColumnList[0].value) // Set default sort column to the first one
        setOffset(0);
        setLimit(20);
        // fetchNotifications(user, channel, seen,
        //     dateStart, dateEnd, limit, offset, sortColumn, sortOrder, message, enabled);
    }, [])

    useEffect(() => {
        fetchNotifications(user, channel, seen,
            dateStart, dateEnd, limit, offset, sortColumn, sortOrder, message, enabled);
    }, [user, channel, seen, dateStart, dateEnd, sortColumn, sortOrder, message, enabled, offset, limit]);

    useEffect(() => {
        fetchTotalItems(user, channel, seen, dateStart, dateEnd, sortColumn, sortOrder, message, enabled);
    }, [user, channel, seen, dateStart, dateEnd, sortColumn, sortOrder, message, enabled]);

    return (
        <div className="flex flex-col grow p-1">

            <div className="flex-row grow mb-4 mt-2 content-center">
                <span className="text-2xl font-semibold text-gray-900">Log History</span>
            </div>

            <div className="flex-row grow border-2 p-2 border-blue-300">

                <div className="flex-row">
                    <label className='p-1 mr-2' htmlFor={"user"}>{"User: "}</label>
                    <select value={user} onChange={e => {
                        setUser(e.target.value)
                    }}
                        className="mr-1 border-2 border-blue-300">
                        <option key={0} value={''} onChange={setUser}>{"None"}</option>
                        {userList && userList.map((it) => (
                            <option key={it.id} value={it.id}>{it.name}</option>
                        ))}
                    </select>


                    <label className='p-1 mr-2' htmlFor={"channel"}>{"Channel: "}</label>
                    <select value={channel} onChange={e => {
                        setChannel(e.target.value)
                    }}
                        className="mr-1 border-2 border-blue-300">
                        <option key={0} value={''} onChange={setChannel}>{"None"}</option>
                        {channelList && channelList.map((it) => (
                            <option key={it.id} value={it.id}>{it.name}</option>
                        ))}
                    </select>

                    <label className='p-1 mr-2' htmlFor={"seen"}>{"Seen: "}</label>
                    <select value={seen} onChange={e => {
                        setSeen(e.target.value)
                    }}
                        className="mr-1 border-2 border-blue-300">
                        <option key={0} value={''} onChange={setSeen}>{"Any"}</option>
                        {booleanList && booleanList.map((it) => (
                            <option key={it.name} value={it.value}>{it.name}</option>
                        ))}
                    </select>

                    <label className='p-1 mr-1' htmlFor={"enabled"}>{"Enabled: "}</label>
                    <select value={enabled} onChange={e => {
                        setEnabled(e.target.value)
                    }}
                        className="mr-1 border-2 border-blue-300">
                        <option key={0} value={''} onChange={setEnabled}>{"Any"}</option>
                        {booleanList && booleanList.map((it) => (
                            <option key={it.name} value={it.value}>{it.name}</option>
                        ))}
                    </select>

                    <label className='p-1 mr-1' htmlFor={"sortColumn"}>{"Sort Column: "}</label>
                    <select value={sortColumn} onChange={e => {
                        setSortColumn(e.target.value)
                    }}
                        className="mr-1 border-2 border-blue-300">
                        {sortColumnList && sortColumnList.map((it) => (
                            <option key={it.name} value={it.value}>{it.name}</option>
                        ))}
                    </select>
                </div>
                <br />

                <div className="flex-row">
                    <label className='p-1 mr-1' htmlFor={"dateStart"}>{"Received Start Date: "}</label>
                    <input
                        type="date"
                        id="dateStart"
                        value={dateStart}
                        onChange={(e) => setDateStart(e.target.value)}
                        className="border border-blue-300 p-2 mr-1" />

                    <label className='p-1 mr-1' htmlFor={"dateEnd"}>{"Received End Date: "}</label>
                    <input
                        type="date"
                        id="dateEnd"
                        value={dateEnd}
                        onChange={(e) => setDateEnd(e.target.value)}
                        className="border border-blue-300 p-2 mr-1" />

                    <label className='p-1 mr-1' htmlFor={"sortOrder"}>{"Sort Order: "}</label>
                    <select value={sortOrder} onChange={e => {
                        setSortOrder(e.target.value)
                    }}
                        className="mr-1 border-2 border-blue-300">
                        {sortOrdersList && sortOrdersList.map((it) => (
                            <option key={it.name} value={it.value}>{it.name}</option>
                        ))}
                    </select>
                </div>

                <br />
                <label className='p-1 mr-2' htmlFor={"message"}>{"Message: "}</label>
                <textarea value={message} onChange={e => setMessage(e.target.value)} className="border-2 border-blue-300"
                    type="input" name="message" id="message" rows={1} cols={50} />

            </div>
            <div className="flex flex-wrap overflow-x-auto mt-2">
                <table className="w-full h-full max-h-32 table-auto border border-separate border-spacing-2 border-blue-300">
                    <thead className="">
                        <tr>
                            <th className="border border-blue-200">Notification ID</th>
                            <th className="border border-blue-200">User</th>
                            <th className="border border-blue-200">Category</th>
                            <th className="border border-blue-200">Channel</th>
                            <th className="border border-blue-200">Message</th>
                            <th className="border border-blue-200">Date Created</th>
                            <th className="border border-blue-200">Status</th>
                            <th className="border border-blue-200">Received Date</th>
                            <th className="border border-blue-200">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {notificationList && notificationList.map((it) => (
                            <tr key={it.id}>
                                <td>{it.id}</td>
                                <td>{it.user}</td>
                                <td>{it.category}</td>
                                <td>{it.channel}</td>
                                <td>{it.message}</td>
                                <td>{new Date(it.dateCreated).toLocaleString()}</td>
                                <td>{it.seen ? "Seen" : "Not Seen"}</td>
                                <td>{it.dateSeen ? new Date(it.dateSeen).toLocaleString() : ""}</td>
                                <td>
                                    <button
                                        hidden={it.seen}
                                        onClick={(event) => handleSubmit(event, it.id, it.userId)}
                                        className="border-2 border-green-200 p-2 bg-green-100"
                                    >
                                        Check
                                    </button></td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <div className="flex-row pt-3">
                <div className="grid grid-cols-2 gap-44">
                    <button
                        onClick={prevPage}
                        className="border-2 border-blue-200 p-2 bg-blue-100"
                    >
                        Previous
                    </button>
                    <button
                        onClick={nextPage}
                        className="border-2 border-blue-200 p-2 bg-blue-100"
                    >
                        Next
                    </button>
                </div>

            </div>
        </div>
    )
}