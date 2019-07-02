<template>
    <div class="main-login">
        <lyz-layout
                :pagination="pagination"
                :label="label"
                @handleSizeChange="handleSizeChange"
                @handleCurrentChange="handleCurrentChange">
            <div slot="banner" class="top-right">
                <el-button type="primary" size="small" @click="createServer" slot="reference">新增</el-button> &nbsp;

                <el-select v-model="queryType" placeholder="请选择查询类型" class="right-select" @change="queryTypeChange"
                           clearable @clear="queryList">
                    <el-option label="服务器名称" value="server_name">服务器名称</el-option>
                    <el-option label="项目名称" value="project_name">项目名称</el-option>
                </el-select>

                <el-input placeholder="请输入搜索内容" v-model="queryKeyword" class="input-with-select" clearable>
                    <el-button slot="append" icon="el-icon-search" @click="queryList"></el-button>
                </el-input>
                <el-button type="danger" size="small" @click="batchRemove" slot="reference">批量删除</el-button> &nbsp;

            </div>
            <div slot="main" class="main-body">
                <el-table
                        :data="tableData"
                        stripe
                        v-loading="loginLoading"
                        tooltip-effect="light"
                        height="100%"
                        style="width: 100%"
                        @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55"></el-table-column>
                    <el-table-column
                            v-for="(data,index) in tableHeader"
                            :show-overflow-tooltip="true"
                            :key="index"
                            :prop="data.prop"
                            :label="data.label"
                            :min-width="data['min-width']"
                            :align="data.align">
                    </el-table-column>
                    <el-table-column
                            fixed="right"
                            label="操作"
                            min-width="120">
                        <template slot-scope="scope">
                            <el-button type="text" size="mini"
                                       :disabled="scope.row.serverStatus === '使用中'"
                                       @click="applyServer(scope.row)">申请
                            </el-button>
                            <el-button type="text" size="mini"
                                       :disabled="scope.row.serverStatus === '空闲'"
                                       @click="releaseServer(scope.row.id)">释放
                            </el-button>
                            <el-button type="text" size="mini" class="danger-text"
                                       @click="deleteServer(scope.row.id)">删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>

        </lyz-layout>
        <el-dialog :title="operate==='apply'?'申请服务器':'添加服务器信息'" :visible.sync="messageVisible" width="33%" center
                   class="user-dialog">
            <el-form :model="messageForm" :label-width="messageLabelWidth" ref="messageForm" :rules="messageRule"
                     :validate-on-rule-change=false>

                <el-form-item label="名称" v-if="operate==='create'" prop="serverName">
                    <el-input v-model="messageForm.serverName" placeholder="请输入服务器名称"></el-input>
                </el-form-item>

                <el-form-item label="状态" v-if="operate==='create'" pop="serverStatus">
                    <el-radio-group v-model="messageForm.serverStatus">
                        <el-radio label="free" checked>空闲</el-radio>
                        <el-radio lebel="used">使用中</el-radio>
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="项目名称" v-if="operate==='apply'" prop="projectName">
                    <el-input v-model="messageForm.projectName" placeholder="请输入项目名称"></el-input>
                </el-form-item>

                <el-form-item label="申请时间" v-if="operate==='apply'" prop="applyTime">
                    <el-date-picker
                            v-model="messageForm.applyTime"
                            type="datetime"
                            value-format="timestamp"
                            placeholder="选择申请时间"
                            align="right"
                            :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>

                <el-form-item label="释放时间" v-if="operate==='apply'" prop="releaseTime">
                    <el-date-picker
                            v-model="messageForm.releaseTime"
                            type="datetime"
                            value-format="timestamp"
                            placeholder="选择释放时间"
                            align="right"
                            :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="messageVisible = false">取 消</el-button>
                <el-button type="primary" @click=saveServer>确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import lyzLayout from '@/components/lyzLayout';
    import manage from '../manage.component';
    import {formatDateTime, responseText, debounce} from '../../../config/utils.js';

    export default {
        name: "server",
        data() {
            return {
                queryType: '',
                queryKeyword: '',
                pagination: {
                    pageIndex: 1,
                    pageSize: 10,
                    total: 0,
                },
                label: '服务器信息管理',
                messageForm: {},
                messageVisible: false,
                messageLabelWidth: '90px',
                messageRule: {
                    serverName: [
                        {required: true, message: '请输入服务器名称', trigger: 'blur'}
                    ],
                    serverStatus: [
                        {required: true, message: '请选择服务器状态', trigger: 'blur'}
                    ],
                    projectName: [
                        {required: true, message: '请输入项目名称', trigger: 'blur'}
                    ],
                    applyTime: [
                        {required: true, message: '请输入申请时间', trigger: 'blur'}
                    ],
                    releaseTime: [
                        {required: true, message: '请输入释放时间', trigger: 'blur'}
                    ]
                },
                operate: '',
                multipleSelection: [],//多选的数据
                pickerOptions: {
                    shortcuts: [{
                        text: '今天',
                        onClick(picker) {
                            picker.$emit('pick', new Date());
                        }
                    }, {
                        text: '一天后',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() + 3600 * 1000 * 24);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '一周后',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() + 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '一月后',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() + 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', date);
                        }
                    }],
                    disabledDate: function (now) {
                        if (now < new Date(new Date().setDate(new Date().getDate()-1)))
                            return true;
                        return false
                    }
                },
                tableData: [],
                loginLoading: false,
                tableHeader: [
                    {
                        prop: 'id',
                        label: 'ID',
                        'min-width': 40,
                        align: 'center',
                    },
                    {
                        prop: 'applyUserName',
                        label: '使用人',
                        'min-width': 80,
                        align: 'center',
                    },
                    {
                        prop: 'serverName',
                        label: '服务器名称',
                        'min-width': 120,
                        align: 'center',
                    },
                    {
                        prop: 'serverStatus',
                        label: '服务器状态',
                        'min-width': 100,
                        align: 'center',
                    },
                    {
                        prop: 'applyTime',
                        label: '申请时间',
                        'min-width': 120,
                        align: 'center',
                    },
                    {
                        prop: 'releaseTime',
                        label: '释放时间',
                        'min-width': 120,
                        align: 'center',
                    },
                    {
                        prop: 'projectName',
                        label: '项目名称',
                        'min-width': 120,
                        align: 'center',
                    },
                ]
            };
        },
        components: {
            lyzLayout,
        },
        mixins: [manage],
        created() {
            this.queryList();
        },
        mounted() {
            this.$watch('queryKeyword', debounce(() => {
                this.pagination.pageIndex = 1;
                this.queryList();
            }, 1000));
        },
        methods: {
            queryTypeChange(val) {
                if (val !== '') {
                    this.queryType = val;
                    this.queryList();
                }
            },
            queryList() {
                this.loginLoading = true;
                let params = {
                    queryType: this.queryType,
                    queryKeyword: this.queryKeyword,
                    pageNo: this.pagination.pageIndex,
                    pageSize: this.pagination.pageSize
                };
                this.$http.get('/server/paging', {params: params}).then(({body}) => {
                    if (body.success === true) {
                        responseText(body.data.records).forEach((item) => {
                            item.serverStatus = item.serverStatus == 'free' ? '空闲' : '使用中';
                            if (item.releaseTime != null) {
                                item.releaseTime = new Date(item.releaseTime).toLocaleString().replace(/:\d{1,2}$/, ' ');
                            }
                            if (item.applyTime != null) {
                                item.applyTime = new Date(item.applyTime).toLocaleString().replace(/:\d{1,2}$/, ' ');
                            }
                        })
                        this.tableData = responseText(body.data.records);
                        this.pagination.total = body.data.records ? body.data.total : 0;
                    } else {
                        this.$message.error(body.message);
                    }
                }).finally(() => {
                    this.loginLoading = false;
                })
            },

            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            batchRemove() {
                let ids = '';
                this.multipleSelection.forEach(selectedItem => {
                    // 删除请求
                    ids += selectedItem.id + ',';
                });
                let params = {
                    ids: ids
                };
                this.$http.post("/server/batchRemove", {}, {params: params}).then(({body}) => {
                    if (body.success === true) {
                        this.queryList();
                        this.$message.success(body.message);
                    } else {
                        this.$message.error(body.message);
                    }
                }).catch(() => {
                    this.$message.error('删除失败');
                })
            },
            deleteServer(id) {
                let params = {
                    id: id,
                };
                this.delete('/server/delete', params);
            },
            createServer(row) {
                console.log(row);
                this.messageVisible = true;
                this.operate = 'create';
                let _form = Object.assign({}, row);
                this.messageForm = _form;
            },
            applyServer(row) {
                console.log(row);
                this.messageVisible = true;
                this.operate = 'apply';
                let _form = Object.assign({}, row);
                this.messageForm = _form;
            },
            saveServer() {
                console.log('save');
                let params = {
                    applyUserName: this.messageForm.applyUserName,
                    serverName: this.messageForm.serverName,
                    projectName: this.messageForm.projectName,
                    applyTime: this.messageForm.applyTime,
                    releaseTime: this.messageForm.releaseTime,
                };
                this.$refs['messageForm'].validate((valid) => {
                    if (valid) {
                        if (params.applyTime > params.releaseTime) {
                            this.$message.error('释放时间必须大于申请时间！');
                        } else {
                            if (this.messageForm.id) {
                                params.serverStatus = this.messageForm.serverStatus == '空闲' ? 'free' : 'used';
                                params.id = this.messageForm.id;
                                this.save('/server/apply', params, 'messageVisible');
                            } else {
                                params.serverStatus = this.messageForm.serverStatus;
                                this.save('/server/create', params, 'messageVisible');
                            }
                        }
                    }
                })
            },
            releaseServer(id) {
                this.$http.post('/server/release?id=' + id, {}, {}).then(({body}) => {
                    if (body.success === true) {
                        this.$message.success(body.message);
                        this.queryList();
                    } else {
                        this.$message.error(body.message);
                    }
                }).catch(() => {
                    this.$message.error('释放失败');
                })
            }

        }
    }
</script>

<style scoped>
    .main-login {
        height: 100%;
    }

    .danger-text {
        color: #F56C6C;
    }
</style>
