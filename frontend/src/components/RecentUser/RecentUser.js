import './RecentUser.css';

export default function RecentUser({props, user}) {
    return (<div className={'recent-user'}>
        <div>
            {user.firstName} {user.lastName}
            {user.email}
        </div>
    </div>);
}


